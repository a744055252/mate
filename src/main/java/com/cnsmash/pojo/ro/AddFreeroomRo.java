package com.cnsmash.pojo.ro;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddFreeroomRo {

    private Long id;

    /**
     * 房间号
     */
    @NotNull
    private String roomId;

    /**
     * 房间密码
     */
    private String password;

    /**
     * 人数上限
     */
    @NotNull
    private Integer maximum;

    /**
     * 服务器
     */
    @NotNull
    private String server;

    /**
     * 房间简述
     */
    private String description;

}
