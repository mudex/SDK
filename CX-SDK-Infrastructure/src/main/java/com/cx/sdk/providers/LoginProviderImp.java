package com.cx.sdk.providers;

import com.cx.sdk.SDKConfigurationProvider;
import com.checkmarx.v7.*;
import com.cx.sdk.login.Session;

/**
 * Created by ehuds on 2/25/2017.
 */
public class LoginProviderImp implements LoginProvider {

    SDKConfigurationProvider sdkConfigurationProvider;

    public LoginProviderImp(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
    }

    public Session login(String userName, String password) {
            CxSDKWebService cxSDKWebService = new CxSDKWebService(sdkConfigurationProvider.getCxServerUrl());
            CxSDKWebServiceSoap cxSDKWebServiceSoap = cxSDKWebService.getCxSDKWebServiceSoap();
            Credentials credentials = new Credentials();
            credentials.setUser(userName);
            credentials.setPass(password);
            CxWSResponseLoginData responseLoginData = cxSDKWebServiceSoap.login(credentials, 1033);
            /*if (responseLoginData == null || !responseLoginData.isIsSuccesfull())
                throw new*/
        return new Session(responseLoginData.getSessionId());
    }
}
