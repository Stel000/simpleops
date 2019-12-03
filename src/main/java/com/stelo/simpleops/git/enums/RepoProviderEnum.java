package com.stelo.simpleops.git.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stelo.simpleops.enums.BaseEnum;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RepoProviderEnum implements BaseEnum {

    GITHUB("github",0);

    private String value;

    private Integer code;

    RepoProviderEnum(String value, Integer code) {
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
    public static RepoProviderEnum getProviderFromCode(Integer code){
        for (RepoProviderEnum e:RepoProviderEnum.values()) {
            if (e.getCode().equals(code)){
                return e;
            }
        }
        return null;
    }

    public static RepoProviderEnum getProviderFromValue(String value){
        for (RepoProviderEnum e:RepoProviderEnum.values()) {
            if (e.getValue().equals(value)){
                return e;
            }
        }
        return null;
    }
}
