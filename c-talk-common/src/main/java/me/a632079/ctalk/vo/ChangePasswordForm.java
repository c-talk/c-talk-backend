package me.a632079.ctalk.vo;

import lombok.Data;

/**
 * @className: ChangePasswordForm
 * @description: ChangePasswordForm - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class ChangePasswordForm {
    // 原密码
    private String oriPassword;

    // 修改密码
    private String password;

    // 确认密码
    private String rePassword;
}
