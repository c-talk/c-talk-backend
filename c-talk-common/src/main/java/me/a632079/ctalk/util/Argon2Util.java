package me.a632079.ctalk.util;

import de.mkammerer.argon2.Argon2;
import me.a632079.ctalk.po.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @className: Argon2Util
 * @description: Argon2Util - Argon2工具类
 * @version: v1.0.0
 * @author: haoduor
 */

@Component
public class Argon2Util {

    @Resource
    private Argon2 argon2;

    public String hash(String data) {
        // TODO 魔法值需要常量化
        return argon2.hash(10, 10240, 1, data.toCharArray());
    }

    public boolean verify(String hash, String data) {
        return argon2.verify(hash, data.toCharArray());
    }

    public boolean verify(User u, String data) {
        return argon2.verify(u.getPassword(), data.toCharArray());
    }
}
