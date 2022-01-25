package com.cnsmash.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.match.MatchBean;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.bean.Room;
import com.cnsmash.pojo.ro.BattleResultRo;
import com.cnsmash.pojo.ro.CreateRoomRo;
import com.cnsmash.pojo.ro.PageBattleRo;
import com.cnsmash.pojo.vo.MatchResultVo;
import com.cnsmash.pojo.vo.PageBattleVo;
import com.cnsmash.service.BattleService;
import com.cnsmash.util.MateAuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author guanhuan_li
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/battle")
public class BattleController {

    @Autowired
    BattleService battleService;

    /**
     * 开始匹配
     * @return 匹配结果
     */
    @PostMapping
    public ReposResult<MatchResultVo> match(){
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        MatchResultVo vo = battleService.match(loginUser.getUserId());
        return ReposResult.ok(vo);
    }

    /**
     * 取消匹配
     * @return 结果
     */
    @PostMapping("/cancel")
    public ReposResult<Void> cancel(){
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        battleService.cancel(loginUser.getUserId());
        return ReposResult.ok();
    }

    /**
     * 获取对战详情
     * @param battleId 对战id
     * @return 详情
     */
    @GetMapping
    public ReposResult<MatchResultVo> get(@RequestParam Long battleId) {
        return ReposResult.ok(battleService.get(battleId));
    }

    /**
     * 创建房间
     * @param ro 创建请求
     * @return 房间信息
     */
    @PostMapping("/room")
    public ReposResult<Room> createRoom(@RequestBody @Valid CreateRoomRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        battleService.createRoom(loginUser.getUserId(), ro);
        return ReposResult.ok();
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

    /**
     * 分页查询对战详情
     * @param ro 请求
     * @return 返回
     */
    @GetMapping("/page")
    public ReposResult<Page<PageBattleVo>> page(@Valid PageBattleRo ro){
        return ReposResult.ok(battleService.pageBattle(ro));
    }

    /**
     * 查询对战排队详情 调试使用
     * @return 返回
     */
    @GetMapping("/wait/all")
    public ReposResult<Map<Long, MatchBean>> allWaitMatch(){
        return ReposResult.ok(battleService.allWaitMatch());
    }

    /**
     * 查询对战排队详情 调试使用
     * @param userId 用户id
     * @return 返回
     */
    @GetMapping("/wait")
    public ReposResult<MatchBean> waitMatch(@RequestParam Long userId){
        return ReposResult.ok(battleService.waitMatch(userId));
    }
}
