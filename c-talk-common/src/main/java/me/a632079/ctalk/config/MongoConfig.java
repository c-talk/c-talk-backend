package me.a632079.ctalk.config;

import cn.hutool.core.lang.Snowflake;
import me.a632079.ctalk.po.BasePo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mapping.callback.EntityCallbacks;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @className: MongoConfig
 * @description: MongoConfig - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Configuration
public class MongoConfig {

    @Resource
    private Snowflake snowflake;

    // @Value("${spring.data.mongodb.uri}")
    // private String uri;
    //
    // @Bean
    // @Primary
    // public MongoDatabaseFactory mongoDatabaseFactory() {
    //     return new SimpleMongoClientDatabaseFactory(uri);
    // }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory databaseFactory, MappingMongoConverter converter) {
        MongoTemplate mongoTemplate = new MongoTemplate(databaseFactory, converter);
        mongoTemplate.setEntityCallbacks(EntityCallbacks.create(new BeforeConverter()));
        return mongoTemplate;
    }

    // 转换器
    private class BeforeConverter implements BeforeConvertCallback<Object> {

        @Override
        public Object onBeforeConvert(Object entity, String collection) {
            if (entity instanceof BasePo) {
                BasePo po = (BasePo) entity;

                if (Objects.isNull(po.getId())) {
                    po.setId(snowflake.nextId());
                }

                if (Objects.isNull(po.getCreateTime())) {
                    po.setCreateTime(LocalDateTime.now());
                } else {
                    po.setUpdateTime(LocalDateTime.now());
                }
            }

            return entity;
        }
    }
}
