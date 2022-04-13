package com.cnsmash.pojo.vo;

import lombok.Data;

/**
 * @author Amaki
 */
@Data
public class UserThumbnailVo {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户TAG
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

}
