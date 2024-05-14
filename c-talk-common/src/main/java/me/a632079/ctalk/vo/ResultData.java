package me.a632079.ctalk.vo;

import lombok.Data;
import me.a632079.ctalk.exception.IErrorCode;

import java.time.LocalDateTime;

/**
 * @className: ResultData
 * @description: ResultData - 通用返回结果
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class ResultData<T> {
    private String code;
    private String message;
    private T      result;

    private final LocalDateTime ts;

    public ResultData (){
        this.ts = LocalDateTime.now();
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode("00");
        resultData.setMessage("成功");
        resultData.setResult(data);
        return resultData;
    }

    public static <T> ResultData<T> error(String code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMessage(message);
        return resultData;
    }

    public static <T> ResultData<T> error(IErrorCode errorCode) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(errorCode.getCode());
        resultData.setMessage(errorCode.getMsg());
        return resultData;
    }
}
