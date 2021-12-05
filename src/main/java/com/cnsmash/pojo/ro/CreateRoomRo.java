package com.cnsmash.pojo.ro;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 创建赛季房间
 * @author guanhuan_li
 */
@Data
public class CreateRoomRo {

    /** 房间号 */
    @NotBlank
    @Length(max = 5, min = 5)
    private String no;

    /** 密码 */
    private String pwd;


}
