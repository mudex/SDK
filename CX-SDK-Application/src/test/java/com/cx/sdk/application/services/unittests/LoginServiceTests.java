package com.cx.sdk.application.services.unittests;

import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.application.services.LoginService;
import com.cx.sdk.application.services.LoginServiceImpl;
import com.cx.sdk.domain.CredentialsValidator;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

/**
 * Created by ehuds on 2/28/2017.
 */
public class LoginServiceTests {
    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";

    private final LoginProvider loginProvider = Mockito.mock(LoginProvider.class);
    private final CredentialsValidator credentialsValidator = Mockito.mock(CredentialsValidator.class);

    private LoginService createService() {
        return new LoginServiceImpl(loginProvider, credentialsValidator);
    }

    @Test
    public void login_validUserPassword_credentialsValidationExecuted() throws SdkException {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        when(loginProvider.login(USERNAME, PASSWORD)).thenReturn(expectedResult);

        // Act
        Session result = loginService.login(USERNAME, PASSWORD);

        // Assert
        verify(credentialsValidator).validate(USERNAME, PASSWORD);
        verify(loginProvider).login(USERNAME, PASSWORD);
        assertEquals(expectedResult, result);
    }

    @Test
    public void login_invalidUserPassword_shouldThrow() throws SdkException {
        // Arrange
        LoginService loginService = createService();
        Mockito.doThrow(new SdkException("OMG! credentials are not valid!!!")).when(credentialsValidator).validate(USERNAME, PASSWORD);
        Session result = null;

        // Act
        try {
            result = loginService.login(USERNAME, PASSWORD);
        } catch (SdkException e) {}

        // Assert
        verify(loginProvider, never()).login(USERNAME, PASSWORD);
        assertNull(result);
    }
}
