package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.exception.CodeException;
import com.cnsmash.exception.ErrorCode;
import com.cnsmash.mapper.*;
import com.cnsmash.pojo.GameStatus;
import com.cnsmash.pojo.bean.SingleBattleDetail;
import com.cnsmash.pojo.dto.PlayerQuarterFighterDto;
import com.cnsmash.pojo.entity.*;
import com.cnsmash.pojo.ro.AddQuarterRo;
import com.cnsmash.pojo.ro.CustomQuarterAttendRo;
import com.cnsmash.pojo.ro.CustomQuarterListRo;
import com.cnsmash.pojo.vo.PageBattleVo;
import com.cnsmash.service.BadgeService;
import com.cnsmash.service.QuarterService;
import com.cnsmash.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 赛季
 * @author guanhuan_li
 */
@Slf4j
@Service
public class QuarterServiceImpl implements QuarterService {

    @Autowired
    QuarterMapper quarterMapper;

    @Autowired
    BattleMapper battleMapper;

    @Autowired
    GameFighterMapper gameFighterMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BadgeService badgeService;

    @Autowired
    BadgeMapper badgeMapper;

    @Autowired

    QuarterPlayerMapper quarterPlayerMapper;

    @Override
    public Quarter getCurrent() {
        QueryWrapper<Quarter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("current", true);
        return quarterMapper.selectOne(queryWrapper);
    }

    @Override
    public void add(AddQuarterRo ro) {

        Quarter codeQuarter = getByCode(ro.getCode());
        if (codeQuarter != null) {
            throw new CodeException(ErrorCode.NICKNAME_EXIT, "赛季编码已存在");
        }

        Quarter quarter = new Quarter();
        BeanUtils.copyProperties(ro, quarter);
        quarter.setCurrent(true);
        // 过期当前赛季
        invalidCurrentQuarter();
        quarterMapper.insert(quarter);
    }

    /**
     * 标记当前赛季过时
     */
    private void invalidCurrentQuarter() {
        QueryWrapper<Quarter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("current", true);

        Quarter quarter = new Quarter();
        quarter.setCurrent(false);

        quarterMapper.update(quarter, queryWrapper);
    }

    private Quarter getByCode(String code) {
        QueryWrapper<Quarter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return quarterMapper.selectOne(queryWrapper);
    }

    /**
     * 定时任务，赛季结算
     */
    //@Scheduled(cron = "0 0 6/24 * * ?")
    @Override
    public void quarterSumup() {
        // 判断赛季是否结束
        Quarter current = getCurrent();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        if (now.after(current.getEndTime())) {
            if (current.getSumup() != null && current.getSumup() == 1) {
                // 赛季已经结算
                return;
            }
        } else {
            // 赛季还未结束
            return;
        }

        // 清理未正确报分对局
        QueryWrapper<Battle> unfinishBattleWrapper = new QueryWrapper<>();
        unfinishBattleWrapper.eq("quarter", current.getCode()).eq("game_status", "ing");
        List<Battle> battleList = battleMapper.selectList(unfinishBattleWrapper);
        for (Battle battle : battleList) {
            battle.setGameStatus("conflict");
            battleMapper.updateById(battle);
        }

        // 获取玩家使用角色信息
        Map<Long, Set<String>> playerMostFighterMap = new HashMap<>();
        Map<Long, Integer> playerMostFighterCount = new HashMap<>();
        Map<String, Set<Long>> fighterTopUsePlayerMap = new HashMap<>();
        Map<String, Long> fighterTopUserPlayerCount = new HashMap<>();
        List<PlayerQuarterFighterDto> playerQuarterFighterDtoList = gameFighterMapper.getPlayerQuarterFighter(current.getCode());
        for (PlayerQuarterFighterDto dto : playerQuarterFighterDtoList) {
            Long id = dto.getUserId();
            String fighter = dto.getFighter();
            Long score = dto.getScore();
            Integer count = dto.getCount();
            // 角色使用次数不得少于5次
            if (count < 5) continue;
            // 判断玩家使用最多角色
            if (!playerMostFighterCount.containsKey(id)) {
                playerMostFighterCount.put(id, count);
                Set<String> set = new HashSet<>();
                set.add(fighter);
                playerMostFighterMap.put(id, set);
            } else {
                if (playerMostFighterCount.get(id) < count) {
                    playerMostFighterCount.put(id, count);
                    Set<String> set = new HashSet<>();
                    set.add(fighter);
                    playerMostFighterMap.put(id, set);
                } else if (playerMostFighterCount.get(id) == count) {
                    Set<String> set = playerMostFighterMap.get(id);
                    set.add(fighter);
                    playerMostFighterMap.put(id, set);
                }
            }
            // 判断成绩最高角色
            if (!fighterTopUserPlayerCount.containsKey(fighter)) {
                fighterTopUserPlayerCount.put(fighter, score);
                Set<Long> set = new HashSet<>();
                set.add(id);
                fighterTopUsePlayerMap.put(fighter, set);
            } else {
                if (fighterTopUserPlayerCount.get(fighter) < score) {
                    playerMostFighterCount.put(id, count);
                    fighterTopUserPlayerCount.put(fighter, score);
                    Set<Long> set = new HashSet<>();
                    set.add(id);
                    fighterTopUsePlayerMap.put(fighter, set);
                } else if (fighterTopUserPlayerCount.get(fighter) == score) {
                    Set<Long> set = fighterTopUsePlayerMap.get(fighter);
                    set.add(id);
                    fighterTopUsePlayerMap.put(fighter, set);
                }
            }
        }
        // 角色榜TOP1徽章
        for (String fighter : fighterTopUsePlayerMap.keySet()) {
            log.info(fighter + ": ");
            Set<Long> userIds = fighterTopUsePlayerMap.get(fighter);
            for (Long userId : userIds) {
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("uri", "fighter-" + fighter + "-red");
                List<Badge> badge = badgeMapper.selectList(wrapper);
                badgeService.addBadge(userId, badge.get(0).getId());
            }
        }
        // 个人main徽章
        for (Long playerId : playerMostFighterMap.keySet()) {
            Set<String> characters = playerMostFighterMap.get(playerId);
            for (String character : characters) {
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("uri", "fighter-" + character + "-green");
                List<Badge> badge = badgeMapper.selectList(wrapper);
                badgeService.addBadge(playerId, badge.get(0).getId());
            }
        }

        // 更新赛季更新状态
        current.setSumup(1);
        quarterMapper.updateById(current);
    }

    /********** custom quarter **********/
    public List<Quarter> getList(CustomQuarterListRo ro) {
        return quarterMapper.page(new Page<>(ro.getCurrent(), ro.getSize()), ro).getRecords();
    }

    public List<Quarter> getAttendList(CustomQuarterListRo ro, Long userId) {
        return quarterMapper.pageAttend(new Page<>(ro.getCurrent(), ro.getSize()), ro, userId).getRecords();
    }

    public String signinQuarter(CustomQuarterAttendRo ro, Long userId) {

        Quarter quarter = quarterMapper.selectById(ro.getQuarterId());

        /** 密码判断 **/
        if (quarter.getPassword().equals("") == false && quarter.getPassword().equals(ro.getPassword()) == false) {
            return "通行密码错误";
        }

        /** 时间判断 **/
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        if (now.after(quarter.getEndTime()) || quarter.getStatus() == "finish") {
            return "赛季已结束";
        }

        /** 已报名判断 **/
        QueryWrapper<QuarterPlayer> wrapper = new QueryWrapper<>();
        wrapper.eq("player_id", userId);
        wrapper.eq("quarter_id", ro.getQuarterId());
        List<QuarterPlayer> quarterPlayers = quarterPlayerMapper.selectList(wrapper);
        if (quarterPlayers.size() > 0) {
            return "已经报名过了";
        }

        QuarterPlayer quarterPlayer = new QuarterPlayer();
        quarterPlayer.setPlayerId(userId);
        quarterPlayer.setQuarterId(ro.getQuarterId());
        quarterPlayer.setBanMap(ro.getBanMap());
        quarterPlayer.setCreateTime(now);
        quarterPlayer.setUpdateTime(now);
        quarterPlayerMapper.insert(quarterPlayer);

        return "success";
    }

    @Override
    public String signinUpdate(CustomQuarterAttendRo ro, Long userId) {

        /** 已报名判断 **/
        QueryWrapper<QuarterPlayer> wrapper = new QueryWrapper<>();
        wrapper.eq("player_id", userId);
        wrapper.eq("quarter_id", ro.getQuarterId());
        List<QuarterPlayer> quarterPlayers = quarterPlayerMapper.selectList(wrapper);
        if (quarterPlayers.size() == 0) {
            return "未报名，操作无效";
        }

        /** 赛季状态判断 **/
        Quarter quarter = quarterMapper.selectById(ro.getQuarterId());
        if (quarter.getStatus().equals("finish")) {
            return "已结束，操作无效";
        }

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        QuarterPlayer quarterPlayer = new QuarterPlayer();
        quarterPlayer.setId(ro.getId());
        quarterPlayer.setPlayerId(userId);
        quarterPlayer.setQuarterId(ro.getQuarterId());
        quarterPlayer.setBanMap(ro.getBanMap());
        quarterPlayer.setCreateTime(now);
        quarterPlayer.setUpdateTime(now);
        quarterPlayerMapper.updateById(quarterPlayer);

        return "success";
    }

    @Override
    public String signout(CustomQuarterAttendRo ro, Long userId) {

        /** 已报名判断 **/
        QueryWrapper<QuarterPlayer> wrapper = new QueryWrapper<>();
        wrapper.eq("player_id", userId);
        wrapper.eq("quarter_id", ro.getQuarterId());
        List<QuarterPlayer> quarterPlayers = quarterPlayerMapper.selectList(wrapper);
        if (quarterPlayers.size() == 0) {
            return "未报名，操作无效";
        }

        /** 赛季状态判断 **/
        Quarter quarter = quarterMapper.selectById(ro.getQuarterId());
        if (quarter.getStatus().equals("before") == false) {
            return "已开始，操作无效";
        }

        quarterPlayerMapper.deleteById(ro.getId());
        return "success";

    }

    @Override
    public String hostStartQuarter(Long quarterId, Long host) {

        Quarter quarter = quarterMapper.selectById(quarterId);

        /** host判断 **/
        if (quarter.getHost() != host) {
            return "非举办人无法开始赛季";
        }

        /** 状态判断 **/
        if (quarter.getStatus() != "before") {
            return "状态错误，无法开始赛季";
        }

        quarter.setStatus("process");
        quarterMapper.updateById(quarter);

        return "赛季已开始";

    }

}
