package com.cx.sdk.application.services.unittests;

import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.application.services.LoginService;
import com.cx.sdk.application.services.LoginServiceImpl;
import com.cx.sdk.domain.Session;
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
    private final SDKConfigurationProvider sdkConfigurationProvider = Mockito.mock(SDKConfigurationProvider.class);

    private LoginService createService() {
        return new LoginServiceImpl(loginProvider, sdkConfigurationProvider);
    }

    @Test
    public void login_shouldSucceed_givenValidUserPassword() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        when(loginProvider.login()).thenReturn(expectedResult);

        // Act
        Session result = loginService.login();

        // Assert
        verify(loginProvider).login();
        assertEquals(expectedResult, result);
    }

    @Test
    public void login_shouldThrow_givenInvalidLoginType() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        when(loginProvider.login()).thenReturn(expectedResult);
        Session result = null;

        // Act
        try {
            result = loginService.login();
        } catch (RuntimeException e) {}

        // Assert
        verify(loginProvider, never()).login();
        assertNull(result);
    }

    @Test
    public void samlLogin_shouldSucceed_givenRightLoginType() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        when(loginProvider.login()).thenReturn(expectedResult);
        // Act
        Session result = loginService.login();

        // Assert
        verify(loginProvider).login();
        assertEquals(expectedResult, result);
    }

    @Test
    public void samlLogin_shouldThrow_givenWrongLoginType() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        Session result = null;

        // Act
        try {
            result = loginService.login();
        } catch (RuntimeException e) {}

        // Assert
        verify(loginProvider, never()).login();
        assertNull(result);
    }

    @Test
    public void ssoLogin_shouldSucceed_givenRightLoginType() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        when(loginProvider.login()).thenReturn(expectedResult);

        // Act
        Session result = loginService.login();

        // Assert
        verify(loginProvider).login();
        assertEquals(expectedResult, result);
    }

    @Test
    public void ssoLogin_shouldThrow_givenWrongLoginType() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        Session result = null;

        // Act
        try {
            result = loginService.login();
        } catch (RuntimeException e) {}

        // Assert
        verify(loginProvider, never()).login();
        assertNull(result);
    }
}
