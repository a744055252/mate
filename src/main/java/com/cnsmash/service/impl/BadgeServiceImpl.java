package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.mapper.BadgeMapper;
import com.cnsmash.mapper.BadgePossessMapper;
import com.cnsmash.mapper.GachaRecordMapper;
import com.cnsmash.mapper.UserMapper;
import com.cnsmash.pojo.GachaPR;
import com.cnsmash.pojo.entity.Badge;
import com.cnsmash.pojo.entity.BadgePossess;
import com.cnsmash.pojo.entity.GachaRecord;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.service.BadgeService;
import com.cnsmash.service.SystemArgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Toddhead
 */
@Slf4j
@Service
public class BadgeServiceImpl implements BadgeService {

    @Autowired
    BadgeMapper badgeMapper;

    @Autowired
    BadgePossessMapper badgePossessMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SystemArgService systemArgService;

    @Autowired
    GachaRecordMapper gachaRecordMapper;

    public List<Badge> getFullList() {
        QueryWrapper<Badge> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("`order`");
        return badgeMapper.selectList(wrapper);
    }

    public List<Badge> getMyBadgeList(Long userId) {
        return badgePossessMapper.getUserBadgeList(userId);
    }

    public void addBadge(Long userId, Long badgeId) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        QueryWrapper<BadgePossess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", userId).eq("badge_id", badgeId);
        if (badgePossessMapper.selectList(queryWrapper).size() == 0) {
            BadgePossess badgePossess = new BadgePossess();
            badgePossess.setPlayerId(userId);
            badgePossess.setBadgeId(badgeId);
            badgePossess.setCreateTime(now);
            badgePossess.setUpdateTime(now);
            badgePossessMapper.insert(badgePossess);
        }
    }

    public String wearBadge(Long badgeId, Long userId) {
        // 检查是否拥有徽章
        QueryWrapper<BadgePossess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", userId).eq("badge_id", badgeId);
        List<BadgePossess> haveBadge = badgePossessMapper.selectList(queryWrapper);
        if (haveBadge.size() == 0) {
            return "无法佩戴未拥有的徽章";
        }

        // 修改佩戴徽章
        User user = userMapper.selectById(userId);
        user.setBadge(badgeId);
        user.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        userMapper.updateById(user);

        return "success";
    }

    public Badge gachaOne() {
        GachaPR gachaPR = systemArgService.getGachaPR();

        Random rnd = new Random();
        List<Integer> seed = new ArrayList<>();
        for (int i=0; i<100; i++) {
            seed.add(i);
        }
        Collections.shuffle(seed, rnd);
        int pick = rnd.nextInt(100);
        int pointer = 0;
        String result = "silver";

        // silver
        for (int c=0; c<gachaPR.getSilver(); c++,pointer++) {
            if (seed.get(pointer) == pick) {
                result = "silver";
            }
        }

        // gold
        for (int c=0; c<gachaPR.getGold(); c++,pointer++) {
            if (seed.get(pointer) == pick) {
                result = "gold";
            }
        }

        // ssr
        for (int c=0; c<gachaPR.getSsr(); c++,pointer++) {
            if (seed.get(pointer) == pick) {
                result = "ssr";
            }
        }

        return badgeMapper.gachaBadge(result);
    }

    @Override
    public List<Badge> gacha(Long userId, int count) {
        User user = userMapper.selectById(userId);

        List<Badge> gachaResult = new ArrayList<>();

        int token = user.getGachaToken();
        if (count == 1) token -= 3;
        if (count == 11) token -= 30;
        // 不够token抽卡
        if (token < 0) {
            return gachaResult;
        }

        String resultStr = "";

        for (int c=0; c<count; c++) {
            Badge gachaBadge = gachaOne();
            addBadge(userId, gachaBadge.getId());
            gachaResult.add(gachaBadge);
            if (resultStr.equals("")) {
                resultStr = gachaBadge.getId() + "";
            } else {
                resultStr += "," + gachaBadge.getId();
            }
        }

        user.setGachaToken(token);
        userMapper.updateById(user);

        // 保存抽奖记录
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        GachaRecord gachaRecord = new GachaRecord();
        gachaRecord.setUserId(userId);
        gachaRecord.setGachaType(count);
        gachaRecord.setGachaResult(resultStr);
        gachaRecord.setCreateTime(now);
        gachaRecord.setUpdateTime(now);
        gachaRecordMapper.insert(gachaRecord);

        return gachaResult;
    }

}
