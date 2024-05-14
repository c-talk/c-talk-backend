package me.a632079.ctalk.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.a632079.ctalk.enums.ChatType;
import me.a632079.ctalk.enums.MessageType;
import me.a632079.ctalk.po.BasePo;

import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
public class MessageDto extends BasePo {
    private String              content;
    private MessageType         type;
    private ChatType            chat_type;
    private Long                from_id;
    private Long                to_id;
    private Map<String, Object> extra_fields;
}
