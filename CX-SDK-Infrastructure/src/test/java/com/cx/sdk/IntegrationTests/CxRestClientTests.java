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
public class CxRestClientTests {

    @Test
    public void login_validCredentails_cxCooliesWhereReturned() throws MalformedURLException {
        // Arrange
        URL serverUrl = new URL("http://10.31.2.118");
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().Create(serverUrl);
        CxRestClient cxRestClient = new CxRestClient(sdkConfigurationProvider);
        String userName = "admin@cx";
        String password = "Cx123456!";

        // Act
        Map<String, String> cookies = cxRestClient.login(userName, password);

        // Assert
        Assert.assertEquals(2, cookies.size());
    }

    @Test(expected = RuntimeException.class)
    public void login_invalidCredentails_cxCooliesWhereReturned() throws MalformedURLException {
        // Arrange
        URL serverUrl = new URL("http://10.31.2.118");
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().Create(serverUrl);
        CxRestClient cxRestClient = new CxRestClient(sdkConfigurationProvider);
        String userName = "adminasds@cx";
        String password = "Cx123456!";

        // Act
        cxRestClient.login(userName, password);
    }


}
