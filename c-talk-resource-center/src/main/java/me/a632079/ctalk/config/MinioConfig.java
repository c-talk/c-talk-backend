package me.a632079.ctalk.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: MinioConfig
 * @description: MinioConfig - minio对象存储
 * @version: v1.0.0
 * @author: haoduor
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    private String endPoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                    .endpoint(endPoint)
                    .credentials(accessKey, secretKey)
                    .build();
    }
}
