package com.cnsmash.service;

import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.vo.MyRankVo;

/**
 * @author guanhuan_li
 */
public interface RankService {

    /**
     * 我的排位
     * @param loginUser 登录人
     * @return 排位数据
     */
    MyRankVo myRank(LoginUser loginUser);

}
