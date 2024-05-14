package me.a632079.ctalk.response;

import cn.hutool.json.JSONUtil;
import me.a632079.ctalk.vo.ResultData;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @className: ResponseAdvice
 * @description: ResponseAdvice - 统一处理消息返回
 * @version: v1.0.0
 * @author: haoduor
 */

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 跳过统一封装
        return !returnType.hasMethodAnnotation(SkipPackage.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Resource
                || body instanceof ResultData<?>
                || body instanceof ResponseEntity) {
            return body;
        }

        if (body instanceof String) {
            return JSONUtil.toJsonStr(ResultData.success(body));
        }

        if (Objects.nonNull(returnType.getMethod())
                && String.class.equals(returnType.getMethod()
                                                 .getReturnType())
                && Objects.isNull(body)) {
            return JSONUtil.toJsonStr(ResultData.success(null));
        }

        return ResultData.success(body);
    }
}
