package com.cnsmash.match;

import java.util.Map;
import java.util.Optional;

/**
 * @author guanhuan_li
 */
public interface MatchHandle {

    /**
     * 匹配房间
     * @param ro 查找房间请求
     * @param waitMatchMap 等待匹配用户
     * @return 匹配的时间
     */
    Optional<MatchBean> match(MatchBean ro, Map<Long, MatchBean> waitMatchMap);



}
