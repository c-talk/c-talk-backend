package me.a632079.ctalk.exception;

/**
 * @className: CTalkExceptionFactory
 * @description: CTalkExceptionFactory - 异常工厂
 * @version: v1.0.0
 * @author: haoduor
 */


public class CTalkExceptionFactory {
    public static CTalkException bizException(IErrorCode errorCode) {
        return new CTalkException(errorCode);
    }

    public static RuntimeException sysException(String message) {
        return new RuntimeException(message);
    }
}
