package com.cx.sdk.providers;

/**
 * Created by ehuds on 2/26/2017.
 */
public interface CredentialsValidator {
    void validate(String userName, String password);
}
