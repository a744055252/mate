package com.cnsmash.service;

import com.cnsmash.pojo.entity.Badge;

import java.util.List;

/**
 * 徽章
 * @author Toddhead
 */
public interface BadgeService {

    List<Badge> getFullList();

    void addBadge(Long userId, Long badgeId);

    List<Badge> getMyBadgeList(Long userId);

    String wearBadge(Long badgeId, Long userId);

    List<Badge> gacha(Long userId, int count);

}
