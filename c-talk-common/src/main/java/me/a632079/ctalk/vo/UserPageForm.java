package me.a632079.ctalk.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className: UserPageForm
 * @description: UserPageForm - 用户查询分页
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageForm extends PageForm {
    private String email;
    private String nickName;
}
