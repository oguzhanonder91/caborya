package com.common.exception;

/**
 * Created by oguzhanonder - 10.10.2018
 */
public class BaseException extends RuntimeException {

    public BaseException(String exception) {
        super(exception);
    }

    public BaseException() {
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

