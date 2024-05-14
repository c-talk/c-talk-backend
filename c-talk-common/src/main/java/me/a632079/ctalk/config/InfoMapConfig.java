package me.a632079.ctalk.config;

import me.a632079.ctalk.po.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @className: InfoMapConfig
 * @description: InfoMapConfig - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Configuration
public class InfoMapConfig {
    @Bean
    public ConcurrentHashMap<Long, UserInfo> userInfoMap() {
        return new ConcurrentHashMap<>(1024);
    }
}
