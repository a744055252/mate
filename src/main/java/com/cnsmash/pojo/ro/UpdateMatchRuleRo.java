package com.cnsmash.pojo.ro;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author guanhuan_li
 */
@Data
public class UpdateMatchRuleRo {

    /** ban图 */
    private Set<String> banMap;

    /**
     * 服务器
     * 裸连Noting 日服Japan 港服Hongkong 美服America
     */
    @NotNull
    private Integer server;

    /**
     * 分差
     */
    @NotNull
    @Min(50)
    private Long scoreGap;

}
