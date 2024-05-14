package me.a632079.ctalk.util;

import lombok.Data;
import me.a632079.ctalk.po.UserInfo;

/**
 * @className: UserInfoUtil
 * @description: UserInfoUtil - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class UserInfoUtil {

    private static final ThreadLocal<UserInfo> userThreadLocal = new ThreadLocal<>();

    public static void setUserInfo(UserInfo info) {
        userThreadLocal.set(info);
    }

    public static void clear() {
        userThreadLocal.remove();
    }

    public static Long getId() {
        return userThreadLocal.get()
                              .getId();
    }
}
