package com.cnsmash.pojo.ro;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BattleMatchRo {

    @NotNull
    private String quarter;

}
