package com.cx.sdk.domain.validators;

import com.cx.sdk.domain.CredentialsValidator;
import com.cx.sdk.domain.exceptions.SdkException;

/**
 * Created by ehuds on 2/26/2017.
 */
public class CredentialsValidatorImpl implements CredentialsValidator {
    @Override
    public void validate(String userName, String password) throws SdkException {
        if (userName == null || userName.isEmpty())
            throw new SdkException("username can't be null or empty");
        if (password == null || password.isEmpty())
            throw new SdkException("password can't be null or empty");
    }
}
