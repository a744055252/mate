package com.cnsmash.service.impl;

import com.cnsmash.exception.CodeException;
import com.cnsmash.exception.ErrorCode;
import com.cnsmash.mapper.BattleGameMapper;
import com.cnsmash.mapper.BattleMapper;
import com.cnsmash.match.MatchHandle;
import com.cnsmash.match.MatchRo;
import com.cnsmash.pojo.BattleType;
import com.cnsmash.pojo.GameStatus;
import com.cnsmash.pojo.RoomRule;
import com.cnsmash.pojo.bean.Room;
import com.cnsmash.pojo.bean.SingleBattleDetail;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.entity.BattleGame;
import com.cnsmash.pojo.entity.Quarter;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.ro.BattleResultRo;
import com.cnsmash.pojo.ro.CreateRoomRo;
import com.cnsmash.pojo.ro.FindRoomRo;
import com.cnsmash.pojo.vo.MyRankVo;
import com.cnsmash.service.*;
import com.cnsmash.util.JsonUtil;
import com.cnsmash.util.SnowFlake;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

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

    /** 等待房间列表 */
    private Set<Room> waitRoomList;

    /** 正在比赛房间列表 */
    private Map<Long, Room> id2gamingRoom;

    @PostConstruct
    public void init() {
        waitRoomList = new CopyOnWriteArraySet<>();
        id2gamingRoom = new ConcurrentHashMap<>(20);
    }


    @Override
    public Room createRoom(Long userId, CreateRoomRo ro) {

        if (!systemArgService.allowCreateRoom()) {
            throw new CodeException(ErrorCode.CREATE_ROOM_ERROR, "当前不允许建房");
        }

        User user = userService.getById(userId);
        MyRankVo rank = rankService.userRank(userId);
        Quarter quarter = quarterService.getCurrent();

        Optional<Room> roomOpt = findByUser(userId);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Room room = roomOpt.orElseGet(()->{
            Room temp = new Room();
            temp.setCreateId(userId);
            temp.setCreateScore(rank.getScore());
            temp.setQuarter(quarter.getCode());
            temp.setRule(RoomRule.single);
            temp.setRoomNum(2);
            temp.setCurrentNum(1);
            return temp;
        });

        // ban图
        Set<String> banMap = JsonUtil.parseJson(user.getBanMap(), new TypeReference<Set<String>>() {
        });
        room.getBanMap().addAll(banMap);

        BeanUtils.copyProperties(ro, room);
        room.setId(SnowFlake.nextId());
        room.setCreateUser(user);
        room.setCreateTime(now);
        waitRoomList.add(room);

        log.info("【{}】创建房间{}", userId, room);

        return room;
    }

    @Override
    public Room findRoom(Long userId, FindRoomRo ro) {

        if (!systemArgService.allowJoinRoom()) {
            throw new CodeException(ErrorCode.JOIN_ROOM_ERROR, "当前不允许匹配");
        }

        User user = userService.getById(userId);
        MyRankVo rank = rankService.userRank(userId);
        MatchRo matchRo = MatchRo.builder().score(rank.getScore()).findTime(ro.getFindTime()).userId(userId).build();
        Optional<Room> roomOpt = matchHandle.match(matchRo, waitRoomList);

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        roomOpt.ifPresent(room -> {

            room.setJoinId(userId);
            room.setJoinScore(rank.getScore());
            room.setStartTime(now);
            room.setJoinUser(user);

            Set<String> banMap = JsonUtil.parseJson(user.getBanMap(), new TypeReference<Set<String>>() {
            });
            room.getBanMap().addAll(banMap);

            // 移出等待列表
            waitRoomList.remove(room);
            // 加入正在比赛列表
            id2gamingRoom.put(room.getId(), room);

            String banMapResult = JsonUtil.toJson(room.getBanMap());
            // 创建对战
            Battle battle = new Battle();
            battle.setQuarter(room.getQuarter());
            battle.setType(BattleType.single.name());
            // 默认bio3
            battle.setGameNum(3);
            battle.setBanMap(banMapResult);
            battle.setGameStatus(GameStatus.ing.name());
            battle.setCreateTime(now);
            battle.setUpdateTime(now);
            battleMapper.insert(battle);

            room.setBattleId(battle.getId());
//            // 开始第一局
//            BattleGame battleGame = new BattleGame();
//            battleGame.setBattleId(battle.getId());
//            battleGame.setSort(1);
//            battleGame.setType(battle.getType());
//            battleGame.setMap("");
//            battleGame.setBanMap(banMapResult);
//            battleGame.setGameStatus(GameStatus.ing.name());
//            battleGameMapper.insert(battleGame);
        });

        return roomOpt.orElse(null);
    }

    @Override
    public void submitBattleResult(Long userId, BattleResultRo ro) {
        if (!id2gamingRoom.containsKey(ro.getRoomId())) {
            throw new CodeException(ErrorCode.BATTLE_ROOM_ERROR, "对战房间不存在");
        }
        Room room = id2gamingRoom.get(ro.getRoomId());
        Battle battle = battleMapper.selectById(room.getBattleId());
        Map<Long, Long> id2score = ro.getId2score();
        long totalScore = id2score.values().stream().mapToLong(score->score).sum();

        // 最多对战次数
        int maxBattle = battle.getGameNum();
        // 最少对战次数
        int minBattle = battle.getGameNum() / 2 + 1;
        if (totalScore > maxBattle || totalScore < minBattle) {
            throw new CodeException(ErrorCode.BATTLE_SCORE_ERROR, "对战分数不对");
        }

        // 一并提交，没有顺序
        int sort = 0;
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        // 创建对局记录
        Long battleWinUserId = null;
        Long maxScore = 0L;
        SingleBattleDetail singleBattleDetail = new SingleBattleDetail();
        Set<String> userFighterSet = ro.getUserFighterSet();
        List<SingleBattleDetail.UserBattleDetail> userBattleDetailList = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : id2score.entrySet()) {
            Long winUserId = entry.getKey();
            Long score = entry.getValue();
            if (score > maxScore) {
                maxScore = score;
                battleWinUserId = winUserId;
            }

            SingleBattleDetail.UserBattleDetail userBattleDetail = new SingleBattleDetail.UserBattleDetail();
            userBattleDetail.setUserId(winUserId);
            userBattleDetail.setBattleScore(score);
            if (!winUserId.equals(userId)) {
                // 对手
                userBattleDetail.setUserFighterSet(userFighterSet);
            }
            userBattleDetailList.add(userBattleDetail);

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
        singleBattleDetail.setUserBattleDetails(userBattleDetailList);
        battle.setUpdateTime(now);
        battle.setBattleWin(battleWinUserId);
        battle.setDetailJson(JsonUtil.toJson(singleBattleDetail));
        battle.setCommitId(userId);
        battle.setGameStatus(GameStatus.end.name());
        battleMapper.updateById(battle);

        // todo加分
    }

    /**
     * 查找用户创建的房间
     * @param userId 用户id
     * @return 房间
     */
    private Optional<Room> findByUser(Long userId) {
        return waitRoomList.stream().filter(room -> room.getCreateId().equals( userId)).findFirst();
    }
}
