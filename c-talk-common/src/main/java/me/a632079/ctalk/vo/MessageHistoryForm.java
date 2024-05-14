package me.a632079.ctalk.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className: MessageHistoryForm
 * @description: MessageHistoryForm - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MessageHistoryForm extends PageForm {
    private Long receiver;
    private Long sender;
}
