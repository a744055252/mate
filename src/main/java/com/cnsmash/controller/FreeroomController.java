package com.cnsmash.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.ro.AddFreeroomRo;
import com.cnsmash.pojo.ro.ListFreeroomRo;
import com.cnsmash.pojo.vo.FreeroomThumbnailVo;
import com.cnsmash.pojo.vo.FreeroomVo;
import com.cnsmash.service.FreeroomService;
import com.cnsmash.util.MateAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Toddhead
 */
@CrossOrigin
@RestController
@RequestMapping("/freeroom")
public class FreeroomController {

    @Autowired
    FreeroomService freeroomService;

    @PostMapping("/add")
    public ReposResult<Void> add(@RequestBody @Valid AddFreeroomRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        freeroomService.add(ro, loginUser.getUserId());
        return ReposResult.ok();
    }

    @PostMapping("/update")
    public ReposResult<Void> update(@RequestBody @Valid AddFreeroomRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        freeroomService.update(ro, loginUser.getUserId());
        return ReposResult.ok();
    }

    @GetMapping("/list")
    public ReposResult<List<FreeroomVo>> page(ListFreeroomRo ro) {
        return ReposResult.ok(freeroomService.list(ro));
    }

    @GetMapping("/detail")
    public ReposResult<FreeroomVo> detail(@RequestParam Long id) {
        return ReposResult.ok(freeroomService.detail(id));
    }

    @GetMapping("/myroom")
    public ReposResult<FreeroomVo> myRoom() {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(freeroomService.detailByUser(loginUser.getUserId()));
    }

    @PostMapping("/shutme")
    public ReposResult<Void> shutdownMyRoom() {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        freeroomService.shutdownRoomByPlayer(loginUser.getUserId());
        return ReposResult.ok();
    }

    @GetMapping("/count")
    public ReposResult<Integer> getCount() {
        return ReposResult.ok(freeroomService.getAvailableCount());
    }

}
