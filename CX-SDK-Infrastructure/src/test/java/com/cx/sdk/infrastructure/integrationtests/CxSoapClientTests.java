package com.cx.sdk.infrastructure.integrationtests;

import com.cx.sdk.infrastructure.CxSoapClient;
import com.cx.sdk.application.contracts.SDKConfigurationProvider;
import com.cx.sdk.infrastructure.SDKConfigurationProviderFactory;
import com.cx.sdk.domain.exceptions.SdkException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ehuds on 2/28/2017.
 */
@Ignore
public class CxSoapClientTests {
    @Test
    public void login_validCredentails_cxCooliesWhereReturned() throws SdkException, MalformedURLException {
        // Arrange
        URL serverUrl = new URL("http://10.31.2.118");
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(serverUrl);
        CxSoapClient cxSoapClient = new CxSoapClient(sdkConfigurationProvider);
        String userName = "admin@cx";
        String password = "Cx123456!";

        // Act
        String session = cxSoapClient.login(userName, password);

        // Assert
        Assert.assertNotNull(session);
    }

    @Test(expected = SdkException.class)
    public void login_invalidCredentails_cxCooliesWhereReturned() throws MalformedURLException, SdkException {
        // Arrange
        URL serverUrl = new URL("http://10.31.2.118");
        SDKConfigurationProvider sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(serverUrl);
        CxSoapClient cxSoapClient = new CxSoapClient(sdkConfigurationProvider);
        String userName = "adminasds@cx";
        String password = "Cx123456!";

        // Act
        cxSoapClient.login(userName, password);
    }
}
