package com.stelo.simpleops.common.exception;


import com.stelo.simpleops.common.enums.BusinessError;

/**
 * 用于处理非编程性的，关于业务的逻辑异常
 */
public class BusinessException extends RootException {

    protected BusinessError businessError;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BusinessException(String msg, Throwable cause, Object... params) {
        super(msg, cause, params);

    }

    public BusinessException(String message, BusinessError businessError) {
        super(message);
        this.businessError = businessError;
    }

    /**
     * @param message    异常信息
     * @param parameters parameters
     */
    public BusinessException(String message, BusinessError businessError, Object... parameters) {
        super(message);
        this.businessError = businessError;
    }

    public BusinessError getBusinessError() {
        return businessError;
    }
}
