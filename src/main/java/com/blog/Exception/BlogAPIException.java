package com.blog.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class BlogAPIException extends RuntimeException{

    private String message;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BlogAPIException(HttpStatus badRequest, String message) {

        super(message);
    }
}
