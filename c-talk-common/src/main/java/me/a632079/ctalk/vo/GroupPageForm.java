package me.a632079.ctalk.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className: GroupPageForm
 * @description: GroupPageForm - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class GroupPageForm extends PageForm {
    private String name;
    private String code;
}
