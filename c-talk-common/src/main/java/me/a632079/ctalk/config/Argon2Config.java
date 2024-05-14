package me.a632079.ctalk.config;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @className: Argon2Config
 * @description: Argon2Config - argon2哈希算法
 * @version: v1.0.0
 * @author: haoduor
 */

@Component
public class Argon2Config {

    @Bean
    public Argon2 argon2() {
        // 默认argon2i 盐16位 hash32位
        return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
    }
}
