package com.stelo.simpleops.common.dto;


import com.stelo.simpleops.common.enums.CustomHttpStatus;

public class BaseResponse {

    private CustomHttpStatus httpStatus;

    private Integer code;

    private String message;

    public BaseResponse() {
    }

    public BaseResponse(CustomHttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    private BaseResponse(Builder builder) {
        setHttpStatus(builder.httpStatus);
        setCode(builder.code);
        setMessage(builder.message);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public CustomHttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(CustomHttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static final class Builder {

        private CustomHttpStatus httpStatus;
        private Integer code;
        private String message;

        private Builder() {
        }

        public Builder httpStatus(CustomHttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public BaseResponse build() {
            return new BaseResponse(this);
        }
    }
}
