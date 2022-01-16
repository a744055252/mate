package com.cnsmash.pojo.ro;

import com.cnsmash.pojo.entity.Battle;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 创建赛季房间
 * @author guanhuan_li
 */
@Data
public class CreateRoomRo {

    /**
     * 对战的id {@link Battle#getId()}
     */
    @NotNull
    private Long battleId;

    /** 房间号 */
    @NotBlank
    @Length(max = 5, min = 5)
    private String no;

    /** 密码 */
    private String pwd;

    /**
     * 服务器
     * 裸连zh 日服jp 港服hk 美服usa
     */
    @NotBlank
    private String server;

}
