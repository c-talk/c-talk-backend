package me.a632079.ctalk.config;

import cn.hutool.core.lang.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @className: IdConfig
 * @description: IdConfig - snowflake
 * @version: v1.0.0
 * @author: haoduor
 */

@Component
public class IdConfig {
    @Value("${snowflake.worker}")
    private long worker;

    @Value("${snowflake.datacenter}")
    private long dataCenter;

    // id 生成器
    @Bean
    public Snowflake snowflake() {
        return new Snowflake(worker, dataCenter);
    }
}
