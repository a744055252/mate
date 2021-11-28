package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemArg extends BaseEntity {

    private Long id;

    /** 系统参数分组id */
    private String groupId;

    /** 分组名称 */
    private String groupName;

    /** 参数名称 */
    private String argName;

    /** 参数key */
    private String argKey;

    /** 参数value */
    private String argValue;

}
