package com.cnsmash.pojo.ro;

import com.cnsmash.pojo.CommentType;
import com.cnsmash.pojo.TournamentStatus;
import com.cnsmash.pojo.bean.PageRo;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class PageTournamentRo extends PageRo {

    /**
     * 比赛状态（0未开始 1进行中 2已结束）
     */
    @NotNull
    private TournamentStatus status;

}
