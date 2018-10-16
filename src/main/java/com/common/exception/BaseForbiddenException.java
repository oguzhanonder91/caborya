package com.common.exception;

/**
 * Created by oguzhanonder - 12.10.2018
 */
public class BaseForbiddenException extends BaseException {

    public BaseForbiddenException(String exception) {
        super(exception);
    }

    public BaseForbiddenException() {
    }

    public BaseForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
