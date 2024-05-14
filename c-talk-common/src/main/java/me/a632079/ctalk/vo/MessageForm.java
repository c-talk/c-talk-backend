package me.a632079.ctalk.vo;

import lombok.Data;
import me.a632079.ctalk.enums.MessageType;

/**
 * @className: MessageForm
 * @description: MessageForm - 消息表单
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class MessageForm {
    private String      content;
    private MessageType type;
    private Long        receiver;
    private Long        sender;
}
