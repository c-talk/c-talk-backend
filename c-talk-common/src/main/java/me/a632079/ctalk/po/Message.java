package me.a632079.ctalk.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.a632079.ctalk.enums.ChatType;
import me.a632079.ctalk.enums.MessageType;
import me.a632079.ctalk.util.MessageUtil;
import org.springframework.data.annotation.Transient;

import java.util.Map;
import java.util.Objects;

/**
 * 私聊消息 message:chat:hash(a_id,b_id)
 * 群聊消息 message:group:g_id
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends BasePo {
    private String      content;
    private MessageType type;

    @Transient
    @JsonIgnore
    private ChatType chatType;

    private Long receiver;
    private Long sender;

    @JsonIgnore
    private String identify;

    private Map<String, Object> extra_fields;

    @Override
    public String getDocumentName() {
        if (Objects.isNull(chatType)) {
            return "";
        }

        switch (chatType) {
            case Private:
                return "message:chat:" + MessageUtil.hash(receiver, sender);
            case Group:
                return "message:group:" + receiver;
            default:
                return "";
        }
    }

    public void genIdent() {
        this.identify = MessageUtil.hash(receiver, sender);
    }
}
