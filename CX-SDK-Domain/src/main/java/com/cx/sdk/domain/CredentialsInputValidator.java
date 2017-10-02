package com.cx.sdk.domain;

/**
 * Created by ehuds on 2/26/2017.
 */

public interface CredentialsInputValidator {
    void validate(String userName, String password);
}
