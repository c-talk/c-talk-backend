package me.a632079.ctalk.config;

import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import lombok.Data;
import me.a632079.ctalk.po.Token;
import me.a632079.ctalk.po.UserInfo;
import me.a632079.ctalk.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * @className: WebSocketConfig
 * @description: WebSocketConfig - websocket服务器
 * @version: v1.0.0
 * @author: haoduor
 */


@Data
@Configuration
public class WebSocketConfig {

    @Value("${ws.server.host}")
    private String host;

    @Value("${ws.server.port}")
    private int port;

    @Resource
    private TokenService tokenService;

    @Resource
    private ConcurrentHashMap<Long, UserInfo> userInfoMap;

    @Bean
    public SocketIOServer server() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);

        // me.a632079.me.a632079.ctalk.me.a632079.ctalk.config.setOrigin("*");
        config.setWorkerThreads(20);

        //这个listener可以用来进行身份验证
        config.setAuthorizationListener(data -> {
            // http://host:${}?token=xxxxxxx
            String token = data.getSingleUrlParam("token");
            Optional<Token> optionalToken = tokenService.getToken(token);

            if (optionalToken.isPresent()) {
                Long uid = optionalToken.get()
                                        .getUid();

                UserInfo info = userInfoMap.get(uid);
                if (Objects.nonNull(info)) {
                    try {
                        info.getClient()
                            .disconnect();
                        info.close();
                    } catch (IOException | TimeoutException e) {
                        e.printStackTrace();
                    }
                }

                return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
            }

            return AuthorizationResult.FAILED_AUTHORIZATION;
        });

        return new SocketIOServer(config);
    }

    // 只有为websocket配置了注解扫描器
    // 对应的注解扫描器才能正常调用@OnConnect, @OnDisconnect, @OnEvent, 等方法
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }
}
