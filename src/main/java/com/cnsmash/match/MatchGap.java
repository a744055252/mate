package com.cnsmash.match;

import lombok.Builder;
import lombok.Data;

/**
 * @author guanhuan_li
 */
@Data
@Builder
public class MatchGap {

    /** 向上分差 */
    private Long upGap;

    /** 向下分差 */
    private Long downGap;

}
