package me.a632079.ctalk.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @className: LoginForm
 * @description: LoginForm - 登录表单
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class LoginForm {
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    // TODO 登录验证码
    private String code;
}
