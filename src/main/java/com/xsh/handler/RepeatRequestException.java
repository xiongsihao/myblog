package com.xsh.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : xsh
 * @create : 2021-01-12 - 21:01
 * @describe:
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class RepeatRequestException extends RuntimeException{
    public RepeatRequestException() {
    }

    public RepeatRequestException(String message) {
        super(message);
    }

    public RepeatRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
