package com.cnsmash.pojo.ro;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

// 更新种子位的RO
@Data
@EqualsAndHashCode(callSuper = false)
public class TournamentSeedingRo {

    private Long tournamentId;

    private List<Long> seeding;

}
