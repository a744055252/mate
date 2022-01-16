package com.cnsmash.pojo.vo;

import com.cnsmash.pojo.bean.Room;
import com.cnsmash.pojo.entity.Battle;
import lombok.Data;

/**
 * @author guanhuan_li
 */
@Data
public class MatchResultVo {

    /**
     * 对战的id {@link Battle#getId()}
     */
    private Long battleId;

    private UserDetail p1;

    private UserDetail p2;

    /**
     * 房间信息
     */
    private Room room;

}
