package com.bxy.indexmaker.service;

public class OSNotRecognisedException extends RuntimeException {

    public static final String INVALID_STATE_ERROR_MESSAGE = "OS is not Linux or Windows";

    public OSNotRecognisedException() {
        this(INVALID_STATE_ERROR_MESSAGE);
    }

    public OSNotRecognisedException(String message) {
        super(message);
    }

    public OSNotRecognisedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OSNotRecognisedException(Throwable cause) {
        super(cause);
    }

    public OSNotRecognisedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
