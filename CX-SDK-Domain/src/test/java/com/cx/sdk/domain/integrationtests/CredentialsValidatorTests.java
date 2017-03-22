package com.cx.sdk.domain.integrationtests;

import com.cx.sdk.domain.CredentialsInputValidator;
import com.cx.sdk.domain.exceptions.SdkException;
import com.cx.sdk.domain.validators.CredentialsInputValidatorImpl;
import org.junit.Test;

/**
 * Created by ehuds on 2/28/2017.
 */
public class CredentialsValidatorTests {
    @Test
    public void validate_validCredentials_noExceptionIsThrown() throws SdkException {
        CredentialsInputValidator credentialsValidator = new CredentialsInputValidatorImpl();
        credentialsValidator.validate("user", "pass");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_nullUsername_exceptionIsThrown() throws SdkException {
        CredentialsInputValidator credentialsValidator = new CredentialsInputValidatorImpl();
        credentialsValidator.validate(null, "pass");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_emptyUsername_exceptionIsThrown() throws SdkException {
        CredentialsInputValidator credentialsValidator = new CredentialsInputValidatorImpl();
        credentialsValidator.validate("", "pass");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_nullPassword_exceptionIsThrown() throws SdkException {
        CredentialsInputValidator credentialsValidator = new CredentialsInputValidatorImpl();
        credentialsValidator.validate("user", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_emptyPassword_exceptionIsThrown() throws SdkException {
        CredentialsInputValidator credentialsValidator = new CredentialsInputValidatorImpl();
        credentialsValidator.validate("user", null);
    }
}
