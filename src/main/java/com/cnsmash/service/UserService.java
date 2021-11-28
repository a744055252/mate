package com.cnsmash.service;

import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.vo.UserDetail;

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
}
