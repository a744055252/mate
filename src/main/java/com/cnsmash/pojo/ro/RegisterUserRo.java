package com.cnsmash.pojo.ro;

import com.cnsmash.pojo.entity.UploadFile;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author guanhuan_li
 */
@Data
public class RegisterUserRo {

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 头像 {@link UploadFile#getId()}
     */
    private Long head;

    /**
     * 手机号
     */
    private String phone;

    private String mail;

    @NotBlank
    private String account;

    @NotBlank
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    private Integer sex;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 自我介绍
     */
    private String intro;

    /**
     * 链接方式
     * 1.有线 2.无线
     */
    private Integer linkType;

}
