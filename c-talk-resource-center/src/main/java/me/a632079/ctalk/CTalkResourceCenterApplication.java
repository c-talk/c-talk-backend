package me.a632079.ctalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@SpringBootApplication
public class CTalkResourceCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CTalkResourceCenterApplication.class, args);
	}

}
