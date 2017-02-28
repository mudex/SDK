package com.cx.sdk;

import com.checkmarx.v7.Credentials;
import com.checkmarx.v7.CxSDKWebService;
import com.checkmarx.v7.CxSDKWebServiceSoap;
import com.checkmarx.v7.CxWSResponseLoginData;



/**
 * Created by ehuds on 2/28/2017.
 */
public class CxSoapClient {
    SDKConfigurationProvider sdkConfigurationProvider;
    public CxSoapClient(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
    }

    public String login(String userName, String password) {
        CxSDKWebService cxSDKWebService = new CxSDKWebService(sdkConfigurationProvider.getCxServerUrl());
        CxSDKWebServiceSoap cxSDKWebServiceSoap = cxSDKWebService.getCxSDKWebServiceSoap();
        Credentials credentials = new Credentials();
        credentials.setUser(userName);
        credentials.setPass(password);
        CxWSResponseLoginData responseLoginData = cxSDKWebServiceSoap.login(credentials, 1033);
        return responseLoginData.getSessionId();
    }
}
