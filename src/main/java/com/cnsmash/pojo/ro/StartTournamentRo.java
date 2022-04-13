package com.cnsmash.pojo.ro;

import com.cnsmash.pojo.CommentType;
import com.cnsmash.pojo.bean.PageRo;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 查询评论记录
 * @author Toddhead
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StartTournamentRo {

    /**
     * 比赛ID
     */
    @NotNull
    private Long tournamentId;


}
