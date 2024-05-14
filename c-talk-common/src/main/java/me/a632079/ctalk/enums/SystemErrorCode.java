package me.a632079.ctalk.enums;

import lombok.AllArgsConstructor;
import me.a632079.ctalk.exception.IErrorCode;

/**
 * @className: SystemErrorCode
 * @description: SystemErrorCode - 系统错误码
 * @version: v1.0.0
 * @author: haoduor
 */

@AllArgsConstructor
public enum SystemErrorCode implements IErrorCode {
    SUCCESS("00", "ok"),

    INTERFACE_INNER_INVOKE_ERROR("01", "内部服务调用错误"),

    SERVER_ERROR("02", "服务端错误"),

    CLIENT_ERROR("03", "客户端错误"),

    ASSERT("04", "校验错误"),

    HTTP_CLIENT_ERROR("05", "调用外部服务错误"),

    HTTP_SERVER_ERROR("06", "调用外部服务错误"),

    NOT_FOUND("07", "非法请求"),

    GATEWAY_ERROR("08", "网关错误"),

    GATEWAY_TIMEOUT("09", "网关超时"),

    BAD_GATEWAY("0S", "服务未启动"),

    UNAVAILABLE("0N", "服务不可用");

    private final String code;

    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
