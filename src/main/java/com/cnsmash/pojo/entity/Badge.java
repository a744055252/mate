package com.cnsmash.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author guanhuan
 * 徽章
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Badge {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     */
    private String uri;

    /**
     * 获取方式
     */
    private String note;

    /**
     * 等级类型
     */
    @TableField(value = "`type`")
    private String type;

    /**
     * 能否抽取
     */
    private Integer gacha;

    /**
     * 排序
     */
    @TableField(value = "`order`")
    private Integer order;
}
