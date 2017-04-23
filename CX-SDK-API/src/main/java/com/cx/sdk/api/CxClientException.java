package com.cx.sdk.api;

/**
 * Created by victork on 23/04/2017.
 */
public class CxClientException extends Exception {
    public CxClientException(String message) {
        super(message);
    }

    public CxClientException(String message, Exception e) {
        super(message, e);
    }
}
