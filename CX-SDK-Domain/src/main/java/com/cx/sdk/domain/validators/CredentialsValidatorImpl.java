package com.cx.sdk.domain.validators;

import com.cx.sdk.domain.CredentialsValidator;

/**
 * Created by ehuds on 2/26/2017.
 */
public class CredentialsValidatorImp implements CredentialsValidator {
    public void validate(String userName, String password) {
        if (userName == null || userName.isEmpty())
            throw new IllegalArgumentException("fewfw");
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("efwfwe");
    }
}
