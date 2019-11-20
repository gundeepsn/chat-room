package com.example.chartroom.enums;

import com.fasterxml.jackson.annotation.JsonValue;

@Deprecated
public class Enums {
    public enum MessageType {
        LOGIN,
        ONLINE_COUNT,
        SPEAK;

        @JsonValue
        public int toValue() {
            return ordinal();
        }
    }
}
