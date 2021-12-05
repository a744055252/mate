package com.cnsmash.service;

import com.cnsmash.pojo.bean.Room;
import com.cnsmash.pojo.ro.CreateRoomRo;
import com.cnsmash.pojo.ro.FindRoomRo;
import com.cnsmash.pojo.ro.BattleResultRo;

/**
 * 对战
 * @author guanhuan_li
 */
public interface BattleService {

    /**
     * 创建赛季房间
     * @param userId 用户id
     * @param ro 创建请求
     * @return 房间信息
     */
    Room createRoom(Long userId, CreateRoomRo ro);

    /**
     * 寻找房间
     * @param userId 用户id
     * @param ro 房间条件
     * @return 房间信息
     */
    Room findRoom(Long userId, FindRoomRo ro);

    /**
     * 提交比赛结果
     * @param userId
     * @param ro
     */
    void submitBattleResult(Long userId, BattleResultRo ro);
}
