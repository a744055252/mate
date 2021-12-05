package com.cnsmash.config;

import com.cnsmash.match.DefaultMatchImpl;
import com.cnsmash.match.MatchHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guanhuan_li
 */
@Slf4j
@Configuration
public class MatchConfig {

    @Bean
    public MatchHandle match() {
        log.info("使用默认匹配规则DefaultMatchImpl");
        return new DefaultMatchImpl();
    }


}
