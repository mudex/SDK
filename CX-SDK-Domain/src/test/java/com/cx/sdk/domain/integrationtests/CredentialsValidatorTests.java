package com.cx.sdk.domain.integrationtests;

import com.cx.sdk.domain.CredentialsValidator;
import com.cx.sdk.domain.exceptions.SdkException;
import com.cx.sdk.domain.validators.CredentialsValidatorImpl;
import org.junit.Test;

/**
 * Created by ehuds on 2/28/2017.
 */
public class CredentialsValidatorTests {
    @Test
    public void validate_validCredentials_noExceptionIsThrown() throws SdkException {
        CredentialsValidator credentialsValidator = new CredentialsValidatorImpl();
        credentialsValidator.validate("user", "pass");
    }

    @Test(expected = SdkException.class)
    public void validate_nullUsername_exceptionIsThrown() throws SdkException {
        CredentialsValidator credentialsValidator = new CredentialsValidatorImpl();
        credentialsValidator.validate(null, "pass");
    }

    @Test(expected = SdkException.class)
    public void validate_emptyUsername_exceptionIsThrown() throws SdkException {
        CredentialsValidator credentialsValidator = new CredentialsValidatorImpl();
        credentialsValidator.validate("", "pass");
    }

    @Test(expected = SdkException.class)
    public void validate_nullPassword_exceptionIsThrown() throws SdkException {
        CredentialsValidator credentialsValidator = new CredentialsValidatorImpl();
        credentialsValidator.validate("user", null);
    }

    @Test(expected = SdkException.class)
    public void validate_emptyPassword_exceptionIsThrown() throws SdkException {
        CredentialsValidator credentialsValidator = new CredentialsValidatorImpl();
        credentialsValidator.validate("user", null);
    }
}
