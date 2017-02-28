package com.cx.sdk.IntegrationTests;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by ehuds on 2/28/2017.
 */
@Ignore
public class CxSoapClientTests {
    @Test
    public void login_validCredentails_cxCooliesWhereReturned() throws MalformedURLException {
        // Arrange
        URL serverUrl = new URL("http://10.31.2.118");
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().Create(serverUrl);
        CxSoapClient cxSoapClient = new CxSoapClient(sdkConfigurationProvider);
        String userName = "admin@cx";
        String password = "Cx123456!";

        // Act
        String session = cxSoapClient.login(userName, password);

        // Assert
        Assert.assertNotNull(session);
    }

    @Test(expected = RuntimeException.class)
    public void login_invalidCredentails_cxCooliesWhereReturned() throws MalformedURLException {
        // Arrange
        URL serverUrl = new URL("http://10.31.2.118");
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().Create(serverUrl);
        CxSoapClient cxSoapClient = new CxSoapClient(sdkConfigurationProvider);
        String userName = "adminasds@cx";
        String password = "Cx123456!";

        // Act
        cxSoapClient.login(userName, password);
    }
}
