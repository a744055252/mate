package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper=false)
public class Challenger extends BaseEntity {

    private int id;

    private Long userId;

    private String note;

    private Timestamp startTime;

    private Timestamp endTime;

}
