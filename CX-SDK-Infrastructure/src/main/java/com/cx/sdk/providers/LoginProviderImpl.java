package com.cx.sdk.providers;

import com.cx.sdk.CxRestClient;
import com.cx.sdk.CxSoapClient;
import com.cx.sdk.SDKConfigurationProvider;
import com.cx.sdk.login.Session;

/**
 * Created by ehuds on 2/25/2017.
 */
public class LoginProviderImpl implements LoginProvider {

    SDKConfigurationProvider sdkConfigurationProvider;
    CxRestClient cxRestClient;
    CxSoapClient cxSoapClient;

    public LoginProviderImpl(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
        cxRestClient = new CxRestClient();
        cxSoapClient = new CxSoapClient(sdkConfigurationProvider);
    }

    public Session login(String userName, String password) {
        return new Session(cxSoapClient.login(userName, password));
    }
}
