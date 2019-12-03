package com.stelo.simpleops.common.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * Root Exception Class
 */
public class RootException extends NestedRuntimeException {

    private Object parameters;

    public RootException(String msg) {
        super(msg);
    }

    public RootException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RootException(String msg, Throwable cause, Object... params) {
        super(msg, cause);
        this.parameters = params;
    }

    public Object getParameters() {
        return parameters;
    }
}
