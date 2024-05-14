package me.a632079.ctalk.vo;

import lombok.Data;

/**
 * @className: UserVo
 * @description: UserVo - 用户
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class UserVo {
    private Long Id;

    private String nickName;

    private String email;

    private String avatar;

    private boolean verify;

    private String token;

    private boolean isFriend;
}
