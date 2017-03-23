package com.cx.sdk.application.services.unittests;

import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.application.services.LoginService;
import com.cx.sdk.application.services.LoginServiceImpl;
import com.cx.sdk.domain.CredentialsInputValidator;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.enums.LoginType;
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
    private final CredentialsInputValidator credentialsValidator = Mockito.mock(CredentialsInputValidator.class);
    private final SDKConfigurationProvider sdkConfigurationProvider = Mockito.mock(SDKConfigurationProvider.class);

    private LoginService createService() {
        return new LoginServiceImpl(loginProvider, credentialsValidator, sdkConfigurationProvider);
    }

    @Test
    public void login_shouldSucceed_givenValidUserPassword() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        when(loginProvider.login(USERNAME, PASSWORD)).thenReturn(expectedResult);
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.CREDENTIALS);
        when(sdkConfigurationProvider.getUsername()).thenReturn(USERNAME);
        when(sdkConfigurationProvider.getPassword()).thenReturn(PASSWORD);

        // Act
        Session result = loginService.login();

        // Assert
        verify(credentialsValidator).validate(USERNAME, PASSWORD);
        verify(loginProvider).login(USERNAME, PASSWORD);
        assertEquals(expectedResult, result);
    }

    @Test
    public void login_shouldThrow_givenInvalidUserPassword() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Mockito.doThrow(new IllegalArgumentException("OMG! credentials are not in a valid format!!!")).when(credentialsValidator).validate(USERNAME, PASSWORD);
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.CREDENTIALS);
        when(sdkConfigurationProvider.getUsername()).thenReturn(USERNAME);
        when(sdkConfigurationProvider.getPassword()).thenReturn(PASSWORD);
        Session result = null;

        // Act
        try {
            result = loginService.login();
        } catch (RuntimeException e) {}

        // Assert
        verify(loginProvider, never()).login(USERNAME, PASSWORD);
        assertNull(result);
    }

    @Test
    public void login_shouldThrow_givenInvalidLoginType() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        when(loginProvider.login(USERNAME, PASSWORD)).thenReturn(expectedResult);
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.SAML);
        when(sdkConfigurationProvider.getUsername()).thenReturn(USERNAME);
        when(sdkConfigurationProvider.getPassword()).thenReturn(PASSWORD);
        Session result = null;

        // Act
        try {
            result = loginService.login();
        } catch (RuntimeException e) {}

        // Assert
        verify(loginProvider, never()).login(USERNAME, PASSWORD);
        assertNull(result);
    }

    @Test
    public void samlLogin_shouldSucceed_givenRightLoginType() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        when(loginProvider.samlLogin()).thenReturn(expectedResult);
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.SAML);

        // Act
        Session result = loginService.samlLogin();

        // Assert
        verify(loginProvider).samlLogin();
        assertEquals(expectedResult, result);
    }

    @Test
    public void samlLogin_shouldThrow_givenWrongLoginType() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.SSO);
        Session result = null;

        // Act
        try {
            result = loginService.samlLogin();
        } catch (RuntimeException e) {}

        // Assert
        verify(loginProvider, never()).samlLogin();
        assertNull(result);
    }

    @Test
    public void ssoLogin_shouldSucceed_givenRightLoginType() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        when(loginProvider.ssoLogin()).thenReturn(expectedResult);
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.SSO);

        // Act
        Session result = loginService.ssoLogin();

        // Assert
        verify(loginProvider).ssoLogin();
        assertEquals(expectedResult, result);
    }

    @Test
    public void ssoLogin_shouldThrow_givenWrongLoginType() throws Exception {
        // Arrange
        LoginService loginService = createService();
        Session expectedResult = mock(Session.class);
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.SAML);
        Session result = null;

        // Act
        try {
            result = loginService.ssoLogin();
        } catch (RuntimeException e) {}

        // Assert
        verify(loginProvider, never()).samlLogin();
        assertNull(result);
    }
}
