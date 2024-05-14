package me.a632079.ctalk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ResourceType {
    Image(1),
    Voice(2),
    File(3);

    @Getter(onMethod_ = @JsonValue)
    private final Integer code;

    @JsonCreator
    public static ResourceType valueOf(int value) {
        for (ResourceType type : values()) {
            if (type.code == value) {
                return type;
            }
        }
        return null;
    }
}
