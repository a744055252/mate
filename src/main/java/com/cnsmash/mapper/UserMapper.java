package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.vo.UserDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author guanhuan_li
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户详情
     * @param userIds 用户id
     * @return 详情列表
     */
    List<UserDetail> listUserDetail(@Param("userIds") List<Long> userIds);

    /**
     * 根据ID获取用户昵称
     * @param id 用户ID
     * @return 对应昵称
     */
    String getNickNameById(@Param("id") Long id);

    /**
     * BAN人啦
     * @param banTime ban结束时间
     * @return
     */
    Void updateBanTime(Long id, Timestamp banTime);

    /**
     * 根据用户ID获取头像地址
     * @param id
     * @return
     */
    String getHeadUrlById(Long id);

    /**
     * 根据AccountId获取主号ID
     * @param id accountId
     * @return 对应主号ID
     */
    Long getMainId(Long id);

}
