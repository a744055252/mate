package com.cnsmash.pojo.ro;

import com.cnsmash.pojo.bean.PageRo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomQuarterListRo extends PageRo {

    private String status;

    private Boolean attend;

}
