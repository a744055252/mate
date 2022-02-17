package com.cnsmash.pojo.vo;

import lombok.Data;

/**
 * @author Toddhead
 */
@Data
public class RuleVo {

    /**
     * 被ban图
     * Set<String> 格式
     */
    private String banMap;

    /**
     * 服务器
     * 裸连1 港服2 日服4 字节流
     */
    private Integer server;

    /**
     * 分差
     */
    private Long scoreGap;

}
