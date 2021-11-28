package com.cnsmash.pojo.bean;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author guanhuan_li
 */
@Data
public class PageRo {

    /** 当前页*/
    @NotNull
    @Min(0)
    private Long current;
    
    /** 页大小 */
    @Min(1)
    private Long size;

}
