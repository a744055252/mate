package com.cnsmash.pojo;

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
    }

    private String auth;

    private Long accountId;

    private Timestamp loginTime;

}
