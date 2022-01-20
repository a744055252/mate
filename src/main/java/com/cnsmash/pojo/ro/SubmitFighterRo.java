package com.cnsmash.pojo.ro;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 对手斗士结果
 * @author guanhuan_li
 */
@Data
public class SubmitFighterRo {

    @NotNull
    private Long battleId;

    /**
     * 对手角色
     */
    private Set<String> userFighterSet;
}
