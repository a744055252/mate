package com.cnsmash.controller;

import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.bean.PageRo;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.entity.Quarter;
import com.cnsmash.pojo.ro.AddQuarterRo;
import com.cnsmash.pojo.ro.CustomQuarterAttendRo;
import com.cnsmash.pojo.ro.CustomQuarterListRo;
import com.cnsmash.pojo.vo.MyRankVo;
import com.cnsmash.service.CommentService;
import com.cnsmash.service.QuarterService;
import com.cnsmash.util.MateAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guanhuan_li
 */
@RestController
@RequestMapping("/quarter")
public class QuarterController {

    @Autowired
    QuarterService quarterService;

    @PostMapping
    public ReposResult<Void> add(@Valid @RequestBody AddQuarterRo ro){
        quarterService.add(ro);
        return ReposResult.ok();
    }

    @GetMapping
    public ReposResult<Quarter> get() {
        return ReposResult.ok(quarterService.getCurrent());
    }

    @GetMapping("/sumup")
    public void sumup() {
        quarterService.quarterSumup();
    }

    /********** custom quarter **********/

    @GetMapping("/list")
    public List<Quarter> getQuarterList(@RequestParam CustomQuarterListRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        List<Quarter> quarterList = new ArrayList<>();
        // 未登录
        if (loginUser == null || ro.getAttend() == false) {
            quarterList = quarterService.getList(ro);
        } else {
            quarterList = quarterService.getAttendList(ro, loginUser.getUserId());
        }
        return quarterList;
    }

    @PostMapping("signin")
    public String signinQuarter(@RequestBody CustomQuarterAttendRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return quarterService.signinQuarter(ro, loginUser.getUserId());
    }

    @PostMapping("signinUpdate")
    public String signinUpdate(@RequestBody CustomQuarterAttendRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return quarterService.signinUpdate(ro, loginUser.getUserId());
    }

    @PostMapping("signout")
    public String signoutQuarter(@RequestBody CustomQuarterAttendRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return quarterService.signout(ro, loginUser.getUserId());
    }

    @PostMapping("startQuarter")
    public String startQuarter(@RequestBody CustomQuarterAttendRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return quarterService.hostStartQuarter(ro.getQuarterId(), loginUser.getUserId());
    }

}
