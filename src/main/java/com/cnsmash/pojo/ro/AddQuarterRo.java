package com.cnsmash.pojo.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author guanhuan_li
 */
@Data
public class AddQuarterRo {

    /** 赛季 */
    @NotBlank
    private String name;

    /** 赛季编码 */
    @NotBlank
    private String code;

    /** 赛季开始时间 */
    @NotNull
    private Timestamp beginTime;

    /** 赛季结束时间 */
    @NotNull
    private Timestamp endTime;

}
