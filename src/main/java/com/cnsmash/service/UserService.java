package com.cnsmash.service;

import com.cnsmash.pojo.BattleResultType;
import com.cnsmash.pojo.entity.Quarter;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.entity.UserFighter;
import com.cnsmash.pojo.ro.AddUserRo;
import com.cnsmash.pojo.ro.UpdateMatchRuleRo;
import com.cnsmash.pojo.vo.HistoryRecordVo;
import com.cnsmash.pojo.vo.RuleVo;
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
     * @param id 用户ID
     * @return 用户详情
     */
    UserDetail getDetailById(Long id);

    /**
     * 新增身份
     * @param ro 请求
     */
    void add(AddUserRo ro);

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

    /**
     * 获取用户匹配规则
     * @param id 用户ID
     * @return 匹配规则
     */
    RuleVo getMyRule(Long id);

    /**
     * 获取用户详细信息
     * @param id 用户id
     * @return 用户详细信息
     */
    UserDetail getUserDetail(Long id);

    /**
     * 根据ID获取用户名称
     * @param id
     * @return 用户名称
     */
    String getNickNameById(Long id);

    /**
     * 根据ID获取用户使用斗士
     * @param id
     * @return 斗士列表
     */
    List<String> getFighterById(Long id);

    /**
     * 查询比赛数据之后ban人
     * @param id 用户ID
     */
    void banUser(Long id);

    /**
     * 根据ID获取头像地址
     * @param id 用户ID
     * @return
     */
    String getHeadUrlById(Long id);

    /**
     * 重新计算角色信息
     */
    void recountFighter();

    /**
     * 添加
     * @param playerId
     * @param gachaToken
     */
    void addGachaToken(Long playerId, int gachaToken);

}
