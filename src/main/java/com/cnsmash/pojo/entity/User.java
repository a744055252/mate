package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BaseEntity {

    private Long id;

    /**
     * 主账号id
     */
    private Long accountId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像 {@link UploadFile#getId()}
     */
    private Long head;

    private String code;

    /**
     * 队伍
     */
    private Long teamId;

    /**
     * 标签
     * List<String> json格式
     */
    private String tagJson;

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
