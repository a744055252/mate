package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.exception.CodeException;
import com.cnsmash.exception.ErrorCode;
import com.cnsmash.mapper.*;
import com.cnsmash.pojo.GameStatus;
import com.cnsmash.pojo.bean.SingleBattleDetail;
import com.cnsmash.pojo.dto.PlayerQuarterFighterDto;
import com.cnsmash.pojo.entity.Badge;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.entity.Quarter;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.ro.AddQuarterRo;
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
        // TODO:发徽章
        for (String fighter : fighterTopUsePlayerMap.keySet()) {
            log.info(fighter + ": ");
            Set<Long> userIds = fighterTopUsePlayerMap.get(fighter);
            for (Long userId: userIds) {
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("note", "fighter-" + fighter + "-red");
                List<Badge> badge = badgeMapper.selectList(wrapper);
                badgeService.addBadge(userId, badge.get(0).getId());
            }
        }
    }
}
