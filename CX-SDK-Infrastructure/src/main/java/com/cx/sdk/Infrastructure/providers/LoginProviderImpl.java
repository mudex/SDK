package com.cx.sdk.Infrastructure.providers;

import com.cx.sdk.Infrastructure.CxRestClient;
import com.cx.sdk.Infrastructure.CxSoapClient;
import com.cx.sdk.application.contracts.SDKConfigurationProvider;
import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.domain.Session;

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
        return new Session(cxSoapClient.login(userName, password),
                            cxRestClient.login(userName, password));
    }
}
