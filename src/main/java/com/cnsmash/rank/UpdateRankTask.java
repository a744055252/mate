package com.cnsmash.rank;

import com.cnsmash.pojo.entity.Quarter;
import com.cnsmash.pojo.entity.UserRank;
import com.cnsmash.service.QuarterService;
import com.cnsmash.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guanhuan_li
 */
@Slf4j
@Component
public class UpdateRankTask {

    @Autowired
    RankService rankService;

    @Autowired
    QuarterService quarterService;

    @Scheduled(cron = "0 */30 * * * ?")
    public void updateRank(){
        log.info("更新用户排名！------start------");
        Quarter current = quarterService.getCurrent();
        List<UserRank> userRanks = rankService.listAll(current.getCode());
        long rank = 1L;
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        for (UserRank userRank : userRanks) {
            userRank.setRank(rank++);
            userRank.setUpdateTime(now);
        }
        rankService.updateBatchById(userRanks);
        log.info("更新用户数量{}", userRanks.size());
        log.info("更新用户排名！------end------");
    }

}

