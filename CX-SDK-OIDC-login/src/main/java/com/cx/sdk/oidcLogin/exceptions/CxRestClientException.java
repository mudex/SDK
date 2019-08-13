package com.cx.sdk.oidcLogin.exceptions;

public class CxRestClientException extends Exception {

    public CxRestClientException(String message) {
        super(message);
    }

    public CxRestClientException(String message, Exception e) {
        super(message, e);
    }
}