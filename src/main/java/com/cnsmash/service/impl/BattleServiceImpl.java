package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.exception.CodeException;
import com.cnsmash.exception.ErrorCode;
import com.cnsmash.mapper.BattleGameMapper;
import com.cnsmash.mapper.BattleMapper;
import com.cnsmash.mapper.GameFighterMapper;
import com.cnsmash.match.MatchBean;
import com.cnsmash.match.MatchHandle;
import com.cnsmash.pojo.*;
import com.cnsmash.pojo.bean.Room;
import com.cnsmash.pojo.bean.SingleBattleDetail;
import com.cnsmash.pojo.entity.*;
import com.cnsmash.pojo.ro.*;
import com.cnsmash.pojo.vo.*;
import com.cnsmash.rank.IRankCountHandle;
import com.cnsmash.service.*;
import com.cnsmash.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author guanhuan_li
 */
@Slf4j
@Service
public class BattleServiceImpl implements BattleService {

    @Autowired
    UserService userService;

    @Autowired
    RankService rankService;

    @Autowired
    QuarterService quarterService;

    @Autowired
    MatchHandle matchHandle;

    @Autowired
    SystemArgService systemArgService;

    @Autowired
    BattleMapper battleMapper;

    @Autowired
    BattleGameMapper battleGameMapper;

    @Autowired
    FileService fileService;

    @Autowired
    GameFighterMapper gameFighterMapper;

    @Autowired
    IRankCountHandle rankCountHandle;

    /** 匹配列表 */
    private Map<Long, MatchBean> waitMatchMap;

    @PostConstruct
    public void init() {
        waitMatchMap = new ConcurrentHashMap<>(20);
    }

    @Override
    public synchronized MatchResultVo match(Long userId) {
        if (!systemArgService.allowMatch()) {
            throw new CodeException(ErrorCode.MATCH_ALLOW_ERROR, "当前不允许匹配");
        }

        Quarter quarter = quarterService.getCurrent();
        User user = userService.getById(userId);

        // 判断是否完成赛季设置
        if (user.getServer() == null) {
            throw new CodeException(ErrorCode.MATCH_ALLOW_ERROR, "请先完成赛季匹配设置");
        }

        // 判断现在是否被ban
        Timestamp banUntil = user.getBanUntil();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        if (now.before(banUntil)) {
            throw new CodeException(ErrorCode.MATCH_ALLOW_ERROR, "当前用户因过多中止而被禁止匹配至" + banUntil.toString().substring(0, 19));
        }

        Battle currentBattle = battleMapper.getCurrentBattle(userId);
        if (currentBattle != null) {
            // 已经有对战
            List<GameFighter> gameFighters = listGameFighterByBattleId(currentBattle.getId());
            // 对手id
            Long targetUserId = null;
            for (GameFighter gameFighter : gameFighters) {
                if (!gameFighter.getUserId().equals(userId)) {
                    targetUserId = gameFighter.getUserId();
                }
            }
            if (targetUserId == null) {
                // 对手不存在
                log.error("对战数据有问题{}", currentBattle.getId());
                throw new CodeException(ErrorCode.BATTLE_ERROR, "对战有问题，请联系管理员！");
            }
            User targetUser = userService.getById(targetUserId);
            MatchResultVo vo = getMatchResultVo(quarter, user, targetUser);
            vo.setBattleId(currentBattle.getId());
            vo.setRoom(JsonUtil.parseJson(currentBattle.getRoomJson(), new TypeReference<Room>() {
            }));
            return vo;
        }

//        Long conflictBattleCount = battleMapper.getConflictBattle(userId);
//        if (conflictBattleCount != 0) {
//            throw new CodeException(ErrorCode.MATCH_ALLOW_ERROR, "存在结果冲突的对局，请重新填写结果后重试");
//        }

        MyRankVo rank = rankService.userRank(userId);
        //Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        MatchBean matchBean = waitMatchMap.computeIfAbsent(userId, (id)->
                MatchBean.builder()
                .userId(userId)
                .score(rank.getScore())
                .server(user.getServer())
                .scoreGap(user.getScoreGap())
                .findTime(now)
                .build());

        Optional<MatchBean> matchOpt = matchHandle.match(matchBean, waitMatchMap);
        if (!matchOpt.isPresent()) {
            return null;
        }

        MatchBean targetMatch = matchOpt.get();
        Long targetUserId = targetMatch.getUserId();

        // 匹配成功 移出队列
        waitMatchMap.remove(userId);
        waitMatchMap.remove(targetUserId);

        User targetUser = userService.getById(targetUserId);

        // ban图
        Set<String> banMap = new HashSet<>();
        banMap.addAll(JsonUtil.parseJson(user.getBanMap(), new TypeReference<Set<String>>() {
        }));
        // ban图
        banMap.addAll(JsonUtil.parseJson(targetUser.getBanMap(), new TypeReference<Set<String>>() {
        }));

        // 创建对战
        Battle battle = new Battle();
        battle.setQuarter(quarter.getCode());
        battle.setType(BattleType.single.name());
        // 默认bio3
        battle.setGameNum(3);
        battle.setBanMap(JsonUtil.toJson(banMap));
        battle.setGameStatus(GameStatus.ing.name());
        battle.setCreateTime(now);
        battle.setUpdateTime(now);
        battleMapper.insert(battle);

        SingleBattleDetail singleBattleDetail = new SingleBattleDetail();
        Map<Long, SingleBattleDetail.UserBattleDetail> userId2detail = new HashMap<>(2);
        singleBattleDetail.setUserId2detail(userId2detail);
        {
            SingleBattleDetail.UserBattleDetail detail = new SingleBattleDetail.UserBattleDetail();
            detail.setUserId(userId);
            detail.setRankScore(rank.getScore());
            detail.setNickName(user.getNickName());
            userId2detail.put(userId, detail);

            // 用户1
            GameFighter gameFighter = new GameFighter();
            gameFighter.setBattleId(battle.getId());
            gameFighter.setUserId(userId);
            gameFighter.setGameFighterStatus(GameFighterStatus.ing.name());
            gameFighter.setUpdateTime(now);
            gameFighter.setCreateTime(now);
            gameFighterMapper.insert(gameFighter);
        }
        {
            SingleBattleDetail.UserBattleDetail detail = new SingleBattleDetail.UserBattleDetail();
            detail.setUserId(targetUserId);
            detail.setRankScore(targetMatch.getScore());
            detail.setNickName(targetUser.getNickName());
            userId2detail.put(targetUserId, detail);

            // 用户2
            GameFighter gameFighter = new GameFighter();
            gameFighter.setBattleId(battle.getId());
            gameFighter.setUserId(targetUser.getId());
            gameFighter.setGameFighterStatus(GameFighterStatus.ing.name());
            gameFighter.setUpdateTime(now);
            gameFighter.setCreateTime(now);
            gameFighterMapper.insert(gameFighter);
        }
        battle.setDetailJson(JsonUtil.toJson(singleBattleDetail));
        battleMapper.updateById(battle);

        MatchResultVo vo = getMatchResultVo(quarter, user, targetUser);
        vo.setBattleId(battle.getId());
        return vo;
    }

    @Override
    public UserBattleStatusVo userBattleStatus(Long userId) {
        UserBattleStatusVo vo = new UserBattleStatusVo();
        vo.setBattleStatus(UserBattleStatus.noting);
        if (waitMatchMap.containsKey(userId)) {
            // 在排队
            MatchBean matchBean = waitMatchMap.get(userId);
            vo.setFindTime(matchBean.getFindTime());
            vo.setBattleStatus(UserBattleStatus.match);
            return vo;
        }
        Battle currentBattle = battleMapper.getCurrentBattle(userId);
        if (currentBattle != null) {
            // 在比赛
            vo.setBattleStatus(UserBattleStatus.battle);
            vo.setBattleId(currentBattle.getId());
        }
        return vo;
    }

    @Override
    public void cancel(Long userId) {
        log.info("用户【{}】取消匹配", userId);
        waitMatchMap.remove(userId);
    }

    @Override
    public MatchResultVo get(Long battleId) {
        Battle battle = battleMapper.selectById(battleId);
        Quarter quarter = quarterService.getCurrent();
        if (battle == null) {
            throw new CodeException(ErrorCode.BATTLE_ERROR, "对战不存在");
        }
        Set<Long> userIdList = listGameFighterByBattleId(battleId).stream()
                .map(GameFighter::getUserId)
                .collect(Collectors.toSet());

        if (userIdList.size() != BattleType.single.getNum()) {
            throw new CodeException(ErrorCode.GAME_NUM_ERROR, "参赛人数有误");
        }
        Iterator<Long> idIter = userIdList.iterator();
        // 玩家1
        User user1 = userService.getById(idIter.next());
        User user2 = userService.getById(idIter.next());
        MatchResultVo vo = getMatchResultVo(quarter, user1, user2);
        vo.setBattleId(battle.getId());
        vo.setBattle(battle);
        if (StringUtils.isNoneBlank(battle.getRoomJson())) {
            vo.setRoom(JsonUtil.parseJson(battle.getRoomJson(), new TypeReference<Room>() {
            }));
        }
        return vo;
    }

    @Override
    public void createRoom(Long userId, CreateRoomRo ro) {

        User user = userService.getById(userId);

        Battle battle = battleMapper.selectById(ro.getBattleId());
        if (battle == null || !GameStatus.ing.name().equals(battle.getGameStatus())) {
            throw new CodeException(ErrorCode.BATTLE_ERROR, "对战结束、终止或不存在");
        }
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Room room = new Room();
        BeanUtils.copyProperties(ro, room);
        room.setCreateId(userId);
        room.setCreateTime(now);
        battle.setUpdateTime(now);
        battle.setRoomJson(JsonUtil.toJson(room));
        battleMapper.updateById(battle);

        // 更新建房时间
        user.setCreateRoomTime(now);
        user.setUpdateTime(now);
        userService.update(user);

    }


    @Override
    public synchronized void submitBattleResult(Long userId, BattleResultRo ro) {
        Battle battle = battleMapper.selectById(ro.getBattleId());
        if (battle == null || !GameStatus.ing.name().equals(battle.getGameStatus())) {
            throw new CodeException(ErrorCode.BATTLE_ERROR, "对战结束、终止或不存在");
        }
        Map<Long, Long> id2score = ro.getId2score();
//        long totalScore = id2score.values().stream().mapToLong(score->score).sum();

//        // 最多对战次数
//        int maxBattle = battle.getGameNum();
//        // 最少对战次数
//        int minBattle = battle.getGameNum() / 2 + 1;
//        if (totalScore > maxBattle || totalScore < minBattle) {
//            throw new CodeException(ErrorCode.BATTLE_SCORE_ERROR, "对战分数不对");
//        }

        // 一并提交，没有顺序
        int sort = 0;
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        // 创建对局记录
        // 对局获胜者
        Long battleWinUserId = 0L;
        Long maxScore = 0L;

        SingleBattleDetail singleBattleDetail = JsonUtil.parseJson(battle.getDetailJson(), new TypeReference<SingleBattleDetail>() {
        });
        Map<Long, SingleBattleDetail.UserBattleDetail> userId2detail = singleBattleDetail.getUserId2detail();

        SingleBattleDetail.UserBattleDetail userBattleDetail = userId2detail.get(userId);
        userBattleDetail.setId2score(id2score);

        // 设置对手角色
        Set<String> userFighterSet = ro.getUserFighterSet();
        if (!CollectionUtils.isEmpty(userFighterSet)) {
            for (Map.Entry<Long, Long> entry : id2score.entrySet()) {
                Long winUserId = entry.getKey();
                SingleBattleDetail.UserBattleDetail detail = userId2detail.get(winUserId);
                if (!winUserId.equals(userId)) {
                    // 对手
                    detail.setUserFighterSet(userFighterSet);
                }
            }
        }

        // 校验比赛结果
        // 双方都提交了比赛结果，且一致
        boolean flag = true;
        Map<Long, Long> defaultSubmitScore = null;
        for (SingleBattleDetail.UserBattleDetail detail : userId2detail.values()) {
            Map<Long, Long> submitScore = detail.getId2score();

            if (submitScore == null || submitScore.isEmpty() || !flag) {
                flag = false;
                break;
            }

            if (defaultSubmitScore == null) {
                defaultSubmitScore = detail.getId2score();
                continue;
            }

            if (defaultSubmitScore.size() != submitScore.size()) {
                flag = false;
                break;
            }

            // 双方提交的用户对应的比分全都相同
            for (Map.Entry<Long, Long> entry : submitScore.entrySet()) {
                Long tempUserId = entry.getKey();
                Long tempScore = entry.getValue();
                Long defaultScore = defaultSubmitScore.get(tempUserId);
                if (defaultScore == null || !defaultScore.equals(tempScore)) {
                    flag = false;
                    break;
                }
            }
        }

        if (!flag) {
            // 比赛不能结束
            log.debug("比赛未结束{}", battle);

            battle.setUpdateTime(now);
            battle.setDetailJson(JsonUtil.toJson(singleBattleDetail));
            battleMapper.updateById(battle);

            // 提交了结果
            updateGameFighterStatus(battle.getId(), userId, GameFighterStatus.submit);
            return;
        }

        // 全部提交完成,比分一致。结束比赛，计算分数
        for (Map.Entry<Long, Long> entry : id2score.entrySet()) {
            Long winUserId = entry.getKey();
            Long score = entry.getValue();
            if (score > maxScore) {
                maxScore = score;
                battleWinUserId = winUserId;
            }

            SingleBattleDetail.UserBattleDetail detail = userId2detail.get(winUserId);
            detail.setBattleScore(score);

            for (; score > 0; score--) {
                BattleGame battleGame = new BattleGame();
                battleGame.setBattleId(battle.getId());
                battleGame.setSort(sort);
                battleGame.setType(battle.getType());
                battleGame.setMap("");
                battleGame.setBanMap(battle.getBanMap());
                battleGame.setGameWinId(winUserId);
                battleGame.setGameStatus(GameStatus.end.name());
                battleGame.setCreateTime(now);
                battleGame.setUpdateTime(now);
                battleGameMapper.insert(battleGame);
            }
        }

        // maxScore改变，表示比赛出现胜负未中止
        if (maxScore != 0) {

            // 更新排位分数
            Map<Long, Long> id2changeScore = updateRankScore(battle.getId(), id2score, battleWinUserId);

            // 角色使用
            // 删除原先添加的没有斗士的数据
            for (SingleBattleDetail.UserBattleDetail detail : userId2detail.values()) {

                Long tempUserId = detail.getUserId();
                BattleResultType type;
                Long changeScore = id2changeScore.get(tempUserId);
                if (!battleWinUserId.equals(tempUserId)) {
                    // 用户没有赢下比赛
                    type = BattleResultType.lose;
                } else {
                    type = BattleResultType.win;
                }
                detail.setType(type);
                detail.setChangeScore(changeScore);
                // 对手
                Set<String> userFighterSetTemp = detail.getUserFighterSet();
                if (!CollectionUtils.isEmpty(userFighterSetTemp)) {
                    // 新增对局使用角色
                    deleteGameFighterByBattleId(battle.getId(), tempUserId);
                    for (String fighter : detail.getUserFighterSet()) {
                        GameFighter gameFighter = new GameFighter();
                        gameFighter.setBattleId(battle.getId());
                        gameFighter.setFighter(fighter);
                        gameFighter.setUserId(detail.getUserId());
                        gameFighter.setGameScore(0);
                        gameFighter.setGameFighterStatus(GameFighterStatus.end.name());
                        gameFighter.setUpdateTime(now);
                        gameFighter.setCreateTime(now);
                        gameFighterMapper.insert(gameFighter);
                    }
                    userService.useFighter(battle.getQuarter(), tempUserId, type, userFighterSetTemp);
                }
            }
        } else {
            // 中止报告
            // TODO:把对应的gameFighter.status修改为submit？
        }

        singleBattleDetail.setUserId2detail(userId2detail);
        battle.setEndTime(now);
        battle.setUpdateTime(now);
        battle.setDetailJson(JsonUtil.toJson(singleBattleDetail));

        // 比赛中止
        if (maxScore == 0) {
            battle.setGameStatus(GameStatus.stop.name());
            battle.setStopReason(ro.getStopReason());
        } else if (maxScore > 0) {
            // 比赛正常结束
            battle.setBattleWin(battleWinUserId);
            battle.setGameStatus(GameStatus.end.name());
        }
        battleMapper.updateById(battle);

        // 更新用户建房时间
        updateCreateRoomTime(battle);

        // 如果比赛结果为中止则ban人
        for (Long playerId : userId2detail.keySet()) {
            userService.banUser(playerId);
        }
    }

    private Map<Long, Long> updateRankScore(Long battleId, Map<Long, Long> id2score, Long battleWinUserId) {
        Map<Long, Long> id2RankScore = new HashMap<>(2);
        for (Long id : id2score.keySet()) {
            MyRankVo rank = rankService.userRank(id);
            id2RankScore.put(id, rank.getScore());
        }
        Map<Long, Long> id2rankResult = rankCountHandle.count(id2score, id2RankScore);

        for (Map.Entry<Long, Long> entry : id2rankResult.entrySet()) {
            Long tempUserId = entry.getKey();
            Long changeScore = entry.getValue();
            BattleResultType type;
            if (battleWinUserId.equals(tempUserId)) {
                // 赢
                type = BattleResultType.win;
            } else {
                type = BattleResultType.lose;
            }
            rankService.submitRank(battleId, tempUserId, type, changeScore);
        }
        return id2rankResult;
    }

    @Override
    public Page<PageBattleVo> pageBattle(PageBattleRo ro) {
        return battleMapper.page(new Page<>(ro.getCurrent(), ro.getSize()), ro);
    }

    @Override
    public MatchBean waitMatch(Long userId) {
        return this.waitMatchMap.get(userId);
    }

    @Override
    public Map<Long, MatchBean> allWaitMatch() {
        return this.waitMatchMap;
    }

    @Override
    public void stop(Long userId, StopBattleRo ro) {
        Battle battle = battleMapper.selectById(ro.getBattleId());
        if (battle == null || !GameStatus.ing.name().equals(battle.getGameStatus())) {
            throw new CodeException(ErrorCode.BATTLE_ERROR, "对战结束、终止或不存在");
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        battle.setGameStatus(GameStatus.stop.name());
        battle.setEndTime(now);
        battle.setStopReason(ro.getStopReason());
        battle.setStopUserId(userId);

        log.info("对战【{}】中止，原因【{}】", battle, ro.getStopReason());
        battleMapper.updateById(battle);
    }

    @Override
    public void submitFighter(Long userId, SubmitFighterRo ro) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Battle battle = battleMapper.selectById(ro.getBattleId());

        if (battle == null || !GameStatus.end.name().equals(battle.getGameStatus())) {
            throw new CodeException(ErrorCode.BATTLE_ERROR, "对战未结束或不存在");
        }

        SingleBattleDetail singleBattleDetail = JsonUtil.parseJson(battle.getDetailJson(), new TypeReference<SingleBattleDetail>() {
        });
        Map<Long, SingleBattleDetail.UserBattleDetail> userId2detail = singleBattleDetail.getUserId2detail();

        // 设置对手角色
        Set<String> userFighterSet = ro.getUserFighterSet();
        for (SingleBattleDetail.UserBattleDetail detail : userId2detail.values()) {
            Long winUserId = detail.getUserId();
            if (!winUserId.equals(userId)) {
                if (!CollectionUtils.isEmpty(detail.getUserFighterSet())) {
                    throw new CodeException(ErrorCode.BATTLE_ERROR, "不能重复提交对手斗士");
                }
                // 对手
                detail.setUserFighterSet(userFighterSet);
                deleteGameFighterByBattleId(battle.getId(), winUserId);
                for (String fighter : detail.getUserFighterSet()) {
                    GameFighter gameFighter = new GameFighter();
                    gameFighter.setBattleId(battle.getId());
                    gameFighter.setFighter(fighter);
                    gameFighter.setUserId(detail.getUserId());
                    gameFighter.setGameScore(0);
                    gameFighter.setUpdateTime(now);
                    gameFighter.setCreateTime(now);
                    gameFighterMapper.insert(gameFighter);
                }

                // 更新用户使用角色
                userService.useFighter(battle.getQuarter(), winUserId, detail.getType(), userFighterSet);
            }
        }
        battle.setDetailJson(JsonUtil.toJson(singleBattleDetail));
        battle.setUpdateTime(now);
        battleMapper.updateById(battle);

    }

    private void deleteGameFighterByBattleId(Long battleId, Long userId) {
        QueryWrapper<GameFighter> wrapper = new QueryWrapper<>();
        wrapper.eq("battle_id", battleId);
        wrapper.eq("user_id", userId);
        gameFighterMapper.delete(wrapper);
    }

    private MatchResultVo getMatchResultVo(Quarter quarter, User user, User targetUser) {
        MatchResultVo vo = new MatchResultVo();
        User p1;
        User p2;
        if (user.getCreateRoomTime().after(targetUser.getCreateRoomTime())) {
            p1 = user;
            p2 = targetUser;
        } else {
            p1 = targetUser;
            p2 = user;
        }
        {
            UserDetail detail = userService.getUserDetail(p1.getId());
            vo.setP1(detail);
        }
        {
            UserDetail detail = userService.getUserDetail(p2.getId());
            vo.setP2(detail);
        }
        return vo;
    }

    private UserDetail getUserDetail(Quarter quarter, User user) {
        UserDetail detail = new UserDetail();
        BeanUtils.copyProperties(user, detail);
        // detail.setHeadSrc(fileService.findById(user.getHead()).getSrc());

        // 排位分
        UserRank userRank = rankService.get(user.getId(), quarter.getCode());
        if (userRank == null) {
            userRank = new UserRank();
            userRank.setUserId(user.getId());
            userRank.setQuarter(quarter.getCode());
            userRank.setRank(0L);
            userRank.setScore(1500L);
            userRank.setWin(0);
            userRank.setLost(0);
            userRank.setTotal(0);
        }
        detail.setUserRank(userRank);

        // 使用角色
        List<UserFighter> userFighters = userService.listUserFighter(user.getId());
        detail.setUserFighterList(userFighters);
        return detail;
    }

    private void updateCreateRoomTime(Battle battle) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Room room = JsonUtil.parseJson(battle.getRoomJson(), new TypeReference<Room>() {
        });
        User user = userService.getById(room.getCreateId());
        // 房间信息可能不存在
        if (user != null) {
            user.setCreateRoomTime(now);
            userService.update(user);
        }
    }

    private List<GameFighter> listGameFighterByBattleId(Long battleId) {
        QueryWrapper<GameFighter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("battle_id", battleId);
        return gameFighterMapper.selectList(queryWrapper);
    }

    private void updateGameFighterStatus(Long battleId, Long userId, GameFighterStatus status) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        QueryWrapper<GameFighter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("battle_id", battleId)
                        .eq("user_id", userId);
        GameFighter gameFighter = new GameFighter();
        gameFighter.setUpdateTime(now);
        gameFighter.setGameFighterStatus(status.name());
        gameFighterMapper.update(gameFighter, queryWrapper);
    }

    public Long getHead2HeadCount(Long userId1, Long userId2) {
        Quarter quarter = quarterService.getCurrent();
        return battleMapper.getHead2HeadCount(userId1, userId2, quarter.getCode());
    }

    public List<Long> getConflict(Long userId) {
        return battleMapper.getConflictBattle(userId);
    }
}
