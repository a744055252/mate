package com.cnsmash.pojo.ro;

import lombok.Data;

@Data
public class PlayerResultListRo {

    /**
     * 选手ID
     */
    private Long id;

    /**
     * 数量限制
     */
    private Integer limit;

}
