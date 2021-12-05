package com.cnsmash.controller;

import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.bean.Room;
import com.cnsmash.pojo.ro.CreateRoomRo;
import com.cnsmash.pojo.ro.FindRoomRo;
import com.cnsmash.pojo.ro.BattleResultRo;
import com.cnsmash.service.BattleService;
import com.cnsmash.util.MateAuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author guanhuan_li
 */
@Slf4j
@RestController
@RequestMapping("/battle")
public class BattleController {

    @Autowired
    BattleService battleService;

    /**
     * 创建房间
     * @param ro 创建请求
     * @return 房间信息
     */
    @PostMapping("/room")
    public ReposResult<Room> createRoom(@RequestBody @Valid CreateRoomRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        Room room = battleService.createRoom(loginUser.getUserId(), ro);
        return ReposResult.ok(room);
    }

    /**
     * 寻找房间
     * @param ro 房间条件
     * @return 房间信息
     */
    @GetMapping("/room")
    public ReposResult<Room> findRoom(@Valid FindRoomRo ro){
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        Room room = battleService.findRoom(loginUser.getUserId(), ro);
        return ReposResult.ok(room);
    }

    /**
     * 提交比赛结果
     * @param ro 比赛结果
     * @return 空
     */
    @PostMapping("/game")
    public ReposResult<Void> submitGameResult(@RequestBody @Valid BattleResultRo ro){
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        battleService.submitBattleResult(loginUser.getUserId(), ro);
        return ReposResult.ok();
    }

}
