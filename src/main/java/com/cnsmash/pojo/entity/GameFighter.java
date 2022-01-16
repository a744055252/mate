package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GameFighter extends BaseEntity {

    private Long id;

    /**
     * 对战的id {@link Battle#getId()}
     */
    private Long battleId;

    /**
     * 对局的id {@link BattleGame#getId()}
     * 为空时，记录比赛参赛人
     */
    private Long gameId;

    /**
     * 参赛用户id
     */
    private Long userId;

    /**
     * 参数用户队伍 teamId 单人比赛为空
     */
    private Long teamId;

    /**
     * 对局分
     */
    private Integer gameScore;

    /**
     * 使用斗士
     */
    private String fighter;
}
