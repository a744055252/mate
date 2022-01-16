package com.cnsmash.pojo.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

/**
 * 比赛结果
 * @author guanhuan_li
 */
@Data
public class BattleResultRo {

    @NotNull
    private Long battleId;

    /** 用户id和对应的比分 */
    @NotEmpty
    private Map<Long, Long> id2score;

    /**
     * 对手角色
     */
    private Set<String> userFighterSet;


}
