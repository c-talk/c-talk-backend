package me.a632079.ctalk.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @className: GroupForm
 * @description: GroupForm - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class GroupForm {
    @NotNull(message = "群组名称不能为空")
    private String name;
    private String desc;
    private String avatar;
    private String banner;
}
