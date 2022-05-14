package com.cnsmash.service;

import com.cnsmash.pojo.bean.Room;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.entity.GameFighter;
import com.cnsmash.pojo.vo.MatchResultVo;

import java.util.List;

/**
 * @author guanhuan_li
 */
public interface WechatService {

    /**
     * 开始比赛发送通知
     * @param vo 对战信息
     */
    void battleBegin(MatchResultVo vo);

    /**
     * 发送建房通知
     * @param battle 对战
     * @param room 房间
     * @param gameFighters 参赛选手
     */
    void createRoom(Battle battle, Room room, List<GameFighter> gameFighters);
}
