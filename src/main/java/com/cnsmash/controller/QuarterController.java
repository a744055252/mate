package com.cnsmash.controller;

import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.bean.PageRo;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.ro.AddQuarterRo;
import com.cnsmash.pojo.vo.MyRankVo;
import com.cnsmash.service.CommentService;
import com.cnsmash.service.QuarterService;
import com.cnsmash.util.MateAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

}
