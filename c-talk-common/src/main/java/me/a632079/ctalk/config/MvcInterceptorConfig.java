package me.a632079.ctalk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.a632079.ctalk.interceptor.CorsInterceptor;
import me.a632079.ctalk.interceptor.UserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: MvcInterceptorConfig
 * @description: MvcInterceptorConfig - 拦截器注册
 * @version: v1.0.0
 * @author: haoduor
 */

@Configuration
public class MvcInterceptorConfig extends WebMvcConfigurationSupport {

    @Resource
    private UserInfoInterceptor userInfoInterceptor;

    @Resource
    private CorsInterceptor corsInterceptor;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        // 跨域请求
        registry.addInterceptor(corsInterceptor)
                .addPathPatterns("/**");

        // 用户信息工具类
        registry.addInterceptor(userInfoInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/ping", "/resources/get/*");
    }

    /**
     * 自定义格式化
     *
     * @param converters
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        ArrayList<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        jackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        converters.add(jackson2HttpMessageConverter);

        super.configureMessageConverters(converters);
    }
}
