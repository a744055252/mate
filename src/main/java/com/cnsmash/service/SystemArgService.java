package com.cnsmash.service;

import com.cnsmash.pojo.Arg;
import com.cnsmash.pojo.entity.SystemArg;

/**
 * @author guanhuan_li
 */
public interface SystemArgService {

    /**
     * 获取系统参数
     * @param arg 参数
     * @return 系统参数
     */
    SystemArg get(Arg arg);

    /**
     * 是否允许建房
     * @return 是否
     */
    boolean allowCreateRoom();

    /**
     * 是否允许匹配
     * @return 是否
     */
    boolean allowJoinRoom();
}
