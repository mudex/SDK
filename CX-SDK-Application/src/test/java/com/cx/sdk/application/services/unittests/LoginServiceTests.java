package com.cx.sdk.application.services.unittests;

import com.cx.sdk.application.contracts.SDKConfigurationProvider;
import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.application.services.LoginService;
import com.cx.sdk.application.services.LoginServiceImpl;
import com.cx.sdk.domain.CredentialsValidator;
import com.cx.sdk.domain.exceptions.SdkException;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by ehuds on 2/28/2017.
 */
public class LoginServiceTests {
    @Test
    public void login_validUserPassword_cradentialsValidationExcuted() throws SdkException {
        // Arrange
        LoginProvider loginProvider = Mockito.mock(LoginProvider.class);
        CredentialsValidator credentialsValidator = Mockito.mock(CredentialsValidator.class);
        LoginService loginService = new LoginServiceImpl(loginProvider, credentialsValidator);

        // Act
        loginService.login("user", "pass");

        // Assert
        Mockito.verify(credentialsValidator, Mockito.times(1)).validate("user", "pass");
    }
}
