package com.cnsmash.pojo.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author guanhuan_li
 */
@Data
public class BaseEntity {

    private Timestamp createTime;

    private Timestamp updateTime;

}
