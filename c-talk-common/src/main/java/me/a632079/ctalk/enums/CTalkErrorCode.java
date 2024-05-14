package me.a632079.ctalk.enums;

import lombok.AllArgsConstructor;
import me.a632079.ctalk.exception.IErrorCode;

/**
 * @className: CTalkErrorCode
 * @description: CTalkErrorCode - 错误码
 * @version: v1.0.0
 * @author: haoduor
 */

@AllArgsConstructor
public enum CTalkErrorCode implements IErrorCode {

    PASSWORD_NOT_PASS("101", "两次密码输入不一致"),

    EMAIl("102", "邮箱已被占用"),

    HAS_LOGIN("103", "用户已登录"),

    EMAIL_OR_PASSWORD_WRONG("201", "邮箱或密码错误"),

    FILE_NOT_EXIST("301", "文件不存在"),

    FILE_EMPTY("302", "文件为空"),

    GROUP_NOT_JOINED("501", "未加入群组"),

    NOT_OWNER_RESOURCE("901", "无权限操作"),

    TODO("998", "空置错误"),

    TEST_ERROR("999", "测试错误");

    private final String code;

    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
