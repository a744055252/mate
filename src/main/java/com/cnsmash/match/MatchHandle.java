package com.cnsmash.match;

import com.cnsmash.pojo.bean.Room;

import java.util.Optional;
import java.util.Set;

/**
 * @author guanhuan_li
 */
public interface MatchHandle {

    /**
     * 匹配房间
     * @param ro 查找房间请求
     * @param roomList 房间列表
     * @return 匹配的时间
     */
    Optional<Room> match(MatchRo ro, Set<Room> roomList);



}
