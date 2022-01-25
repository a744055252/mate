package com.cnsmash.pojo.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author guanhuan_li
 * #date  2022/1/20 17:40
 */
@Data
public class StopBattleRo {

    @NotNull
    private Long battleId;

    /**
     * 中止原因
     */
    @NotBlank
    private String stopReason;
}
