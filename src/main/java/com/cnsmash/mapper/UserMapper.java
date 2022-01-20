package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.vo.UserDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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

}
