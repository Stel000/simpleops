package com.stelo.simpleops.git.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stelo.simpleops.enums.BaseEnum;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EventStatus implements BaseEnum {
    SUCCESS("成功",1), FAIL("失败", 0);

    private String value;

    private Integer code;

    EventStatus(String value, Integer code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public Integer getCode() {
        return code;
    }

    @JsonCreator
    public static EventStatus getProviderFromCode(Integer code){
        for (EventStatus e: EventStatus.values()) {
            if (e.getCode().equals(code)){
                return e;
            }
        }
        return null;
    }

    public static EventStatus getProviderFromValue(String value){
        for (EventStatus e: EventStatus.values()) {
            if (e.getValue().equals(value)){
                return e;
            }
        }
        return null;
    }
}
