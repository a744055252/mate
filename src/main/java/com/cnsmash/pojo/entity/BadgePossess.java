package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 对战
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BadgePossess extends BaseEntity {

    private Long id;

    private Long playerId;

    private Long badgeId;

}
