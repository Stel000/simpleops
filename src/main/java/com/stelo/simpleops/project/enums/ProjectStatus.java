package com.stelo.simpleops.project.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stelo.simpleops.enums.BaseEnum;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProjectStatus implements BaseEnum {

    WAITING("待验证",0),NORMAL("已验证",1);

    private String value;

    private Integer code;

    ProjectStatus(String value, Integer code) {
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
    public static ProjectStatus getPropertyFromCode(Integer code){
        for (ProjectStatus e:ProjectStatus.values()) {
            if (e.getCode().equals(code)){
                return e;
            }
        }
        return null;
    }

    public static ProjectStatus getPropertyFromValue(String value){
        for (ProjectStatus e:ProjectStatus.values()) {
            if (e.getValue().equals(value)){
                return e;
            }
        }
        return null;
    }
}
