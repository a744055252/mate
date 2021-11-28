package com.cnsmash.pojo.ro;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author guanhuan_li
 */
@Data
public class HeadRo {
    /** 用户id列表 */
    @NotNull
    @NotEmpty
    private List<Long> userIds;
}
