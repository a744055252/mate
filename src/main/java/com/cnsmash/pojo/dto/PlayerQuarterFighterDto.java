package com.cnsmash.pojo.dto;

import lombok.Data;

/**
 * @author Toddhead
 * 统计选手赛季使用角色数据
 */
@Data
public class PlayerQuarterFighterDto {

    private Long userId;

    private String fighter;

    private Integer count;

    private Long score;

}
