package me.a632079.ctalk.response;

import java.lang.annotation.*;

/**
 * @className: SkipPackage
 * @description: SkipPackage - 跳过统一封装
 * @version: v1.0.0
 * @author: haoduor
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SkipPackage {
}
