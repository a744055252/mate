package com.cnsmash.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BaseEntity{
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 主账号id
     */
    private Long accountId;

    /**
     * 昵称
     */
    private String nickName;


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
