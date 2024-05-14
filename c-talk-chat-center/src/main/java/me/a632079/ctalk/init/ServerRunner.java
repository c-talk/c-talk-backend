package me.a632079.ctalk.init;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @className: ServerRunner
 * @description: ServerRunner - 启动项
 * @version: v1.0.0
 * @author: haoduor
 */
@Slf4j
@Component
public class ServerRunner implements CommandLineRunner {
    @Resource
    private SocketIOServer server;

    @Override
    public void run(String... args) throws Exception {
        // 开启websocket服务器
        server.startAsync();
    }
}
