package com.cnsmash.pojo.ro;

import com.cnsmash.pojo.bean.PageRo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageFighterRo extends PageRo {

    /**
     * 斗士编码
     */
    private String fighterNo;

    /**
     * 赛季
     */
    private String quarter;

}
