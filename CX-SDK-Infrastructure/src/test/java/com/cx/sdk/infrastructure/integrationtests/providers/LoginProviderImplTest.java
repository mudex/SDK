package com.cx.sdk.infrastructure.integrationtests.providers;

import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;
import com.cx.sdk.infrastructure.SDKConfigurationProviderFactory;
import com.cx.sdk.infrastructure.providers.LoginProviderImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by nirli on 17/05/2017.
 */
public class LoginProviderImplTest extends ProviderTestBase {

    private static final String VALID_USERNAME = "admin@cx";
    private static final String VALID_PASSWORD = "Cx123456!";
    private static final String VALID_SERVER = "http://10.31.2.217";
    private static final String INVALID_SERVER_URL = "http://sgsdf.adf";
    private static final String VALID_SERVER_WITHOUT_CX_CLIENT = "http://www.google.com";

    @Test
    public void login_shouldSucceed_givenValidServer() throws Exception {

        // Arrange
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(getUrl(VALID_SERVER), null, null, null, null);
        LoginProviderImpl loginProvider = new LoginProviderImpl(sdkConfigurationProvider);

        // Act
        Session session = null;
        try {
            session = loginProvider.login(VALID_USERNAME, VALID_PASSWORD);
        } catch (Exception e) {
        }

        // Assert
        Assert.assertTrue(session != null);

    }

    @Test(expected = SdkException.class)
    public void login_shouldThrow_givenInvalidServerAddress() throws Exception {

        // Arrange
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(getUrl(INVALID_SERVER_URL), null, null, null, null);
        LoginProviderImpl loginProvider = new LoginProviderImpl(sdkConfigurationProvider);

        // Act & Assert
        loginProvider.login(VALID_USERNAME, VALID_PASSWORD);
    }

    @Test
    public void login_shouldThrow_givenServerWithoutCxClient() throws Exception {

        // Arrange
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(getUrl(VALID_SERVER_WITHOUT_CX_CLIENT), null, null, null, null);
        LoginProviderImpl loginProvider = new LoginProviderImpl(sdkConfigurationProvider);

        // Act
        Session session = null;
        try {
            session = loginProvider.login(VALID_USERNAME, VALID_PASSWORD);
        } catch (Exception e) {
        }

        // Assert
        Assert.assertTrue(session == null);

    }

    @Test
    @Ignore("Need server with valid SSO login")
    public void ssoLogin_shouldSucceed_givenValidServer() throws Exception {

        // Arrange
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(getUrl(VALID_SERVER), null, null, null, null);
        LoginProviderImpl loginProvider = new LoginProviderImpl(sdkConfigurationProvider);

        // Act
        Session session = null;
        try {
            session = loginProvider.ssoLogin();
        } catch (Exception e) {
        }

        // Assert
        Assert.assertTrue(session != null);

    }

    @Test
    public void ssoLogin_shouldThrow_givenInvalidServerAddress() throws Exception {

        // Arrange
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(getUrl(INVALID_SERVER_URL), null, null, null, null);
        LoginProviderImpl loginProvider = new LoginProviderImpl(sdkConfigurationProvider);

        // Act
        Session session = null;
        try {
            loginProvider.ssoLogin();
        } catch (Exception e) {
        }

        // Assert
        Assert.assertTrue(session == null);

    }

    @Test
    public void ssoLogin_shouldThrow_givenServerWithoutCxClient() throws Exception {

        // Arrange
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(getUrl(VALID_SERVER_WITHOUT_CX_CLIENT), null, null, null, null);
        LoginProviderImpl loginProvider = new LoginProviderImpl(sdkConfigurationProvider);

        // Act
        Session session = null;
        try {
            loginProvider.ssoLogin();
        } catch (Exception e) {
        }

        // Assert
        Assert.assertTrue(session == null);

    }

    @Test
    @Ignore("Opens OKTA page")
    public void samlLogin_shouldSucceed_givenValidServer() throws Exception {

        // Arrange
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(getUrl(VALID_SERVER), null, null, null, null);
        LoginProviderImpl loginProvider = new LoginProviderImpl(sdkConfigurationProvider);

        // Act
        Session session = null;
        try {
            session = loginProvider.samlLogin();
        } catch (Exception e) {
        }

        // Assert
        Assert.assertTrue(session != null);

    }

    @Test
    public void samlLogin_shouldThrow_givenInvalidServerAddress() throws Exception {

        // Arrange
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(getUrl(INVALID_SERVER_URL), null, null, null, null);
        LoginProviderImpl loginProvider = new LoginProviderImpl(sdkConfigurationProvider);

        // Act
        Session session = null;
        try {
            session = loginProvider.samlLogin();
        } catch (Exception e) {
        }

        // Assert
        Assert.assertTrue(session == null);

    }

    @Test
    public void samlLogin_shouldThrow_givenServerWithoutCxClient() throws Exception {

        // Arrange
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(getUrl(VALID_SERVER_WITHOUT_CX_CLIENT), null, null, null, null);
        LoginProviderImpl loginProvider = new LoginProviderImpl(sdkConfigurationProvider);

        // Act
        Session session = null;
        try {
            loginProvider.samlLogin();
        } catch (Exception e) {
        }

        // Assert
        Assert.assertTrue(session == null);

    }
}
