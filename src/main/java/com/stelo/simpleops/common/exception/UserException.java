package com.stelo.simpleops.common.exception;

import com.stelo.simpleops.common.enums.BusinessError;

public class UserException extends BusinessException {
    public UserException(String msg) {
        super(msg);
    }

    public UserException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserException(String msg, Throwable cause, Object... params) {
        super(msg, cause, params);
    }

    public UserException(String message, BusinessError businessError) {
        super(message, businessError);
    }

    public UserException(String message, BusinessError businessError, Object... parameters) {
        super(message, businessError, parameters);
    }
}
