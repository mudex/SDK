package com.cx.sdk.domain.validators;

import com.cx.sdk.domain.CredentialsInputValidator;
import com.cx.sdk.domain.exceptions.SdkException;

/**
 * Created by ehuds on 2/26/2017.
 */
public class CredentialsInputValidatorImpl implements CredentialsInputValidator {
    @Override
    public void validate(String userName, String password) {
        if (userName == null || userName.isEmpty())
            throw new IllegalArgumentException("username can't be null or empty");
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("password can't be null or empty");
    }
}
