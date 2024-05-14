package me.a632079.ctalk.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className: JoinedGroupForm
 * @description: JoinedGroupForm - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class JoinedGroupForm extends PageForm {
    private Long   uid;
    private String groupName;
}
