package me.a632079.ctalk.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @className: RegisterForm
 * @description: RegisterForm - 注册表单
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class RegisterForm {
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @Email(message = "邮箱格式错误")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    private String rePassword;
}
