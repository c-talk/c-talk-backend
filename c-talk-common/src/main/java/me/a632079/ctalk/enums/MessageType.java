package me.a632079.ctalk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MessageType {
    Text(1),
    Picture(2),
    Voice(3);

    @Getter(onMethod_ = @JsonValue)
    private final Integer code;

    @JsonCreator
    public static MessageType valueOf(int value) {
        for (MessageType type : values()) {
            if (type.code == value) {
                return type;
            }
        }
        return null;
    }
}
