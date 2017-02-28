package com.cx.sdk.domain;

import com.cx.sdk.domain.exceptions.SdkException;

/**
 * Created by ehuds on 2/26/2017.
 */
@FunctionalInterface
public interface CredentialsValidator {
    void validate(String userName, String password) throws SdkException;
}
