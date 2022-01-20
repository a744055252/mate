package com.cnsmash.service;

import com.cnsmash.pojo.BattleResultType;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.entity.UserFighter;
import com.cnsmash.pojo.ro.UpdateMatchRuleRo;
import com.cnsmash.pojo.vo.UserDetail;

import java.util.Collection;
import java.util.List;

/**
 * @author guanhuan_li
 */
public interface UserService {

    /**
     * 获取用户id'
     * @param id 用户id
     * @return 用户
     */
    User getById(Long id);

    /**
     * 获取用户详情
     * @param userIds 用户id
     * @return 用户详情
     */
    List<UserDetail> listUserDetail(List<Long> userIds);

    /**
     * 获取用户的身份
     * @param accountId 账号id
     * @return 身份列表
     */
    List<User> listByAccountId(Long accountId);

    /**
     * 获取用户
     * @param nickName 昵称
     * @return 用户
     */
    User getUserByNickName(String nickName);

    /**
     * 添加用户
     * @param user 用户
     */
    void add(User user);

    /**
     * 更新用户
     * @param user 用户
     */
    void update(User user);

    /**
     * 更新匹配规则
     * @param userId  用户
     * @param ro 匹配规则
     */
    void updateMatchRule(Long userId, UpdateMatchRuleRo ro);

    /**
     * 使用角色列表
     * @param quarter     赛季
     * @param userId      用户
     * @param type        对战结果
     * @param fighterList 斗士列表
     */
    void useFighter(String quarter, Long userId, BattleResultType type, Collection<String> fighterList);

    /**
     * 获取用户使用角色
     * @param userId 用户id
     * @return 角色列表
     */
    List<UserFighter> listUserFighter(Long userId);
}
