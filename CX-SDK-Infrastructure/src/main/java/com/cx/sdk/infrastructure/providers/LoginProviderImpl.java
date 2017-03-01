package com.cx.sdk.infrastructure.providers;

import com.cx.sdk.infrastructure.CxRestClient;
import com.cx.sdk.infrastructure.CxSoapClient;
import com.cx.sdk.application.contracts.SDKConfigurationProvider;
import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;

import javax.inject.Inject;

/**
 * Created by ehuds on 2/25/2017.
 */
public class LoginProviderImpl implements LoginProvider {

    SDKConfigurationProvider sdkConfigurationProvider;
    CxRestClient cxRestClient;
    CxSoapClient cxSoapClient;


    @Inject
    public LoginProviderImpl(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
        cxRestClient = new CxRestClient(sdkConfigurationProvider);
        cxSoapClient = new CxSoapClient(sdkConfigurationProvider);
    }

    @Override
    public Session login(String userName, String password) throws SdkException {
        return new Session(cxSoapClient.login(userName, password),
                            cxRestClient.login(userName, password));
    }
}
