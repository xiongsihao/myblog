package com.xsh.handler;

/**
 * @author xsh
 * @create 2020-02-18 21:05
 */
public class StateErrorException extends Exception {
    public StateErrorException() {
        super();
    }

    public StateErrorException(String message) {
        super(message);
    }

    public StateErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public StateErrorException(Throwable cause) {
        super(cause);
    }

    protected StateErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
