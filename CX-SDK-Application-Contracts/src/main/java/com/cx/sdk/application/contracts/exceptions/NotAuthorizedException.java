package com.cx.sdk.application.contracts.exceptions;

import com.cx.sdk.domain.exceptions.SdkException;

/**
 * Created by victork on 23/03/2017.
 */
public class NotAuthorizedException extends SdkException {
    public NotAuthorizedException()  {
        this("Not Authorized");
    }

    public NotAuthorizedException(String message) {
        super(message);
    }
}
