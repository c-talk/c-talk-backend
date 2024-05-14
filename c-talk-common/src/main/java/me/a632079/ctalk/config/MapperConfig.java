package me.a632079.ctalk.config;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @className: MapperConfig
 * @description: MapperConfig - bean转换
 * @version: v1.0.0
 * @author: haoduor
 */

@Component
public class MapperConfig {

    @Bean
    public MapperFacade mapperFacade() {
        return new DefaultMapperFactory.Builder()
                .build()
                .getMapperFacade();
    }
}
