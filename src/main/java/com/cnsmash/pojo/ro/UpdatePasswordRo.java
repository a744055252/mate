package com.cnsmash.pojo.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author guanhuan_li
 */
@Data
public class UpdatePasswordRo {

    /** 旧密码 */
    @NotBlank
    private String oldPassword;

    /** 新密码 */
    @NotBlank
    private String newPassword;

}
