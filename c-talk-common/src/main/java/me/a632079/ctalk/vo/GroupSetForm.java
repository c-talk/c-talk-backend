package me.a632079.ctalk.vo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @className: GroupSetForm
 * @description: GroupSetForm - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class GroupSetForm {
    @NotNull(message = "id不能为空")
    private Long id;

    private String name;

    @Size(min = 3, max = 10, message = "自定义邀请码不能少于3位大于10位")
    private String code;

    @Max(value = 200, message = "简介不能超过200字符")
    private String desc;

    private String avatar;
    private String banner;
}
