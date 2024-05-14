package me.a632079.ctalk.exception;

import cn.dev33.satoken.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import me.a632079.ctalk.constant.CTalkConstant;
import me.a632079.ctalk.enums.SystemErrorCode;
import me.a632079.ctalk.vo.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @className: GlobalExceptionAdvice
 * @description: GlobalExceptionAdvice - 全局异常处理
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 自定义异常处理
     *
     * @param ece
     *            me.a632079.me.a632079.ctalk.exception
     * @return ResultData
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CTalkException.class})
    public ResultData<Object> exception(CTalkException ece, HttpServletRequest request) {
        IErrorCode errorInfo = ece.getErrorCode();
        Exception e = ece;
        log.error("[自定义异常]: [{}] [{}] [{}]", request.getRequestURI(), errorInfo.getCode(), errorInfo.getMsg(), e);
        return ResultData.error(errorInfo.getCode(), errorInfo.getMsg());
    }

    @ExceptionHandler(NotLoginException.class)
    public ResponseEntity<?> handlerNotLoginException(NotLoginException nle) {
        log.error("[未登录异常访问]: [{}]", nle.getLoginType(), nle);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * 字段验证异常处理
     *
     * @param request request
     * @param e       异常信息
     * @return ResultData
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultData<Object> methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                              HttpServletRequest request) {
        String msg = getMsg(e);
        log.error("[字段验证异常拦截]: [{}] [{}]", request.getRequestURI(), msg, e);
        return ResultData.error(SystemErrorCode.ASSERT.getCode(), msg);
    }

    private static String getMsg(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        StringBuilder errorMsg = new StringBuilder();
        for (FieldError err : errors) {
            errorMsg.append(err.getDefaultMessage()).append(", ");
        }
        String msg = errorMsg.toString();
        return StringUtils.isBlank(msg) ? msg : msg.substring(0, msg.length() - 2);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultData<?> handleNoHandlerFoundException(HttpServletRequest request, NoHandlerFoundException e) {
        log.error("[Servlet异常]: [{}]", request.getRequestURI(), e);
        SystemErrorCode notFound = SystemErrorCode.NOT_FOUND;
        return ResultData.error(notFound.getCode(), notFound.getMsg());
    }

    /**
     * 运行时异常处理
     *
     * @param e
     *            me.a632079.me.a632079.ctalk.exception
     * @param request
     *            request
     * @return ResultData
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<Object> runtimeException(RuntimeException e, HttpServletRequest request) {
        log.error("[运行时异常]: [{}]", request.getRequestURI(), e);
        return ResultData.error(SystemErrorCode.SERVER_ERROR.getCode(), getMsg(e));
    }

    private static String getMsg(Exception e) {
        String msg = null;
        Throwable cause = e.getCause();
        if (Objects.nonNull(cause)) {
            msg = cause.getMessage();
        }
        if (StringUtils.isBlank(msg)) {
            msg = e.getMessage();
        }
        return StringUtils.isBlank(msg) ? CTalkConstant.GLOBAL_ERROR_MSG : msg;
    }

    /**
     * 异常处理
     *
     * @param e
     *            me.a632079.me.a632079.ctalk.exception
     * @param request
     *            request
     * @return ResultData
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<Object> exception(Exception e, HttpServletRequest request) {
        log.error("[编译时异常]: [{}]", request.getRequestURI(), e);
        return ResultData.error(SystemErrorCode.SERVER_ERROR.getCode(), getMsg(e));
    }
}