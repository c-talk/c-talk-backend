package me.a632079.ctalk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ChatType {
    Private(1),
    Group(2);

    @Getter(onMethod_ = @JsonValue)
    private final Integer value;

    ChatType(int type) {
        this.value = type;
    }

    @JsonCreator
    public static ChatType valueOf(int value) {
        for (ChatType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }

    public int value() {
        return this.value;
    }
}
