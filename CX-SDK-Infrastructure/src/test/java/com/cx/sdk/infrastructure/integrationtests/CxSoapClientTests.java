package com.cx.sdk.infrastructure.integrationtests;

import com.checkmarx.v7.CxWSResponseLoginData;
import com.cx.sdk.infrastructure.CxSoapClient;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.infrastructure.SDKConfigurationProviderFactory;
import com.cx.sdk.domain.exceptions.SdkException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ehuds on 2/28/2017.
 */
@Ignore
public class CxSoapClientTests {
    @Test
    public void login_validCredentails_sessionGeneratedSuccessfully() throws Exception, MalformedURLException {
        // Arrange
        URL serverUrl = new URL("http://10.31.2.118");
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(serverUrl, null, null, null, null);
        CxSoapClient cxSoapClient = new CxSoapClient(sdkConfigurationProvider);
        String userName = "admin@cx";
        String password = "Cx123456!";

        // Act
        CxWSResponseLoginData session = cxSoapClient.login(userName, password);

        // Assert
        Assert.assertNotNull(session);
    }

    @Test(expected = SdkException.class)
    public void login_invalidCredentails_throwException() throws Exception {
        // Arrange
        URL serverUrl = new URL("http://10.31.2.118");
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(serverUrl, null, null, null, null);
        CxSoapClient cxSoapClient = new CxSoapClient(sdkConfigurationProvider);
        String userName = "adminasds@cx";
        String password = "Cx123456!";

        // Act
        cxSoapClient.login(userName, password);
    }

    @Test
    public void ssoLogin_validCredentails_sessionGeneratedSuccessfully() throws Exception {
        // Arrange
        URL serverUrl = new URL("http://10.31.2.118");
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(serverUrl, null, null, null, null);
        CxSoapClient cxSoapClient = new CxSoapClient(sdkConfigurationProvider);

        // Act
        CxWSResponseLoginData session = cxSoapClient.ssoLogin();

        // Assert
        Assert.assertNotNull(session);
    }

    @Test
    public void ssoLogin_kerberosAuthentivationConfigured_sessionGeneratedSuccessfully() throws Exception {
        // Arrange
        URL serverUrl = new URL("http://ehudserver1605");
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(serverUrl, null, null, null, null, true);
        File file = new File("resources/login.conf");
        System.setProperty("java.security.auth.login.config", file.getAbsolutePath());
        System.setProperty("com.sun.security.auth.module.Krb5LoginModule", "true");
        CxSoapClient cxSoapClient = new CxSoapClient(sdkConfigurationProvider);

        // Act
        CxWSResponseLoginData session = cxSoapClient.ssoLogin();

        // Assert
        Assert.assertNotNull(session);
    }
}
