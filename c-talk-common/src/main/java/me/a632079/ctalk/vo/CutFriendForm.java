package me.a632079.ctalk.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @className: CutFriendForm
 * @description: CutFriendForm - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class CutFriendForm {
    @NotNull(message = "用户id不能为空")
    private Long uid;

    @NotNull(message = "好友id不能为空")
    private Long friendId;
}
