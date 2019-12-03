package com.stelo.simpleops.git.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stelo.simpleops.enums.BaseEnum;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EventsType implements BaseEnum {
    PUSH_EVENT("push",0), PULL_REQUEST_EVENT("pullRequest", 1), ISSUES_EVENT("issues", 2);

    private String value;

    private Integer code;

    EventsType(String value, Integer code) {
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
    public static EventsType getProviderFromCode(Integer code){
        for (EventsType e:EventsType.values()) {
            if (e.getCode().equals(code)){
                return e;
            }
        }
        return null;
    }

    public static EventsType getProviderFromValue(String value){
        for (EventsType e:EventsType.values()) {
            if (e.getValue().equals(value)){
                return e;
            }
        }
        return null;
    }
}
