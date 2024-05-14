package me.a632079.ctalk.constant;

/**
 * @className: CTalkConstant
 * @description: CTalkConstant - 公共常量
 * @version: v1.0.0
 * @author: haoduor
 */


public interface CTalkConstant {
    String GLOBAL_ERROR_MSG = "请求错误，请稍后重试";

    // token 过期时间 单位s
    int TOKEN_EXPIRE_TIME = 30 * 60 + 30;

    interface GroupRole {
        String OWNER  = "owner";
        String ADMIN  = "admin";
        String MEMBER = "member";
    }
}
