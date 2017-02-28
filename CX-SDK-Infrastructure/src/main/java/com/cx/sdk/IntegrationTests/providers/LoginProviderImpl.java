package com.cx.sdk.IntegrationTests.providers;

import com.cx.sdk.IntegrationTests.CxRestClient;
import com.cx.sdk.IntegrationTests.CxSoapClient;
import com.cx.sdk.IntegrationTests.SDKConfigurationProvider;
import com.cx.sdk.IntegrationTests.login.Session;

/**
 * Created by ehuds on 2/25/2017.
 */
public class LoginProviderImpl implements LoginProvider {

    SDKConfigurationProvider sdkConfigurationProvider;
    CxRestClient cxRestClient;
    CxSoapClient cxSoapClient;

    public LoginProviderImpl(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
        cxRestClient = new CxRestClient(sdkConfigurationProvider);
        cxSoapClient = new CxSoapClient(sdkConfigurationProvider);
    }

    public Session login(String userName, String password) {
        return new Session(cxSoapClient.login(userName, password));
    }
}
