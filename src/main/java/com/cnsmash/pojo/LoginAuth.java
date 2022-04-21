package com.cnsmash.pojo;

import com.cnsmash.pojo.entity.WxUser;
import com.cnsmash.pojo.vo.AccountUserVo;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author guanhuan_li
 */
@Data
public class LoginAuth {

    public LoginAuth(){}

    public LoginAuth(AccountUserVo vo){
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        this.accountId = vo.getAccount().getId();
        this.auth = vo.getAuth();
        this.loginTime = now;
        this.wxUserId = vo.getWxUserId();
    }

    private String auth;

    private Long accountId;

    private Timestamp loginTime;

    /**
     * 绑定的微信信息id
     * {@link WxUser#getId()}
     */
    private Long wxUserId;

}
