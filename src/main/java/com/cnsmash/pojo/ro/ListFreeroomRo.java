package com.cnsmash.pojo.ro;

import lombok.Data;

/**
 * @author Toddhead
 */
@Data
public class ListFreeroomRo {

    /**
     * 服务器
     */
    private String server;

    /**
     * 人数上限
     */
    private Integer maximum;

    /**
     * 列表长度
     */
    private Integer limit;

}
