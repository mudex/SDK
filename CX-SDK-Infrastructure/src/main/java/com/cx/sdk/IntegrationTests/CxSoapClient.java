package com.cx.sdk.IntegrationTests;

import com.checkmarx.v7.Credentials;
import com.checkmarx.v7.CxSDKWebService;
import com.checkmarx.v7.CxSDKWebServiceSoap;
import com.checkmarx.v7.CxWSResponseLoginData;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by ehuds on 2/28/2017.
 */
public class CxSoapClient {
    SDKConfigurationProvider sdkConfigurationProvider;
    public CxSoapClient(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
    }

    public String login(String userName, String password) {
        URL wsdlUrl = getWsdlUrl(sdkConfigurationProvider.getCxServerUrl());
        CxSDKWebService cxSDKWebService = new CxSDKWebService(wsdlUrl);
        CxSDKWebServiceSoap cxSDKWebServiceSoap = cxSDKWebService.getCxSDKWebServiceSoap();
        Credentials credentials = new Credentials();
        credentials.setUser(userName);
        credentials.setPass(password);

        CxWSResponseLoginData responseLoginData = cxSDKWebServiceSoap.login(credentials, 1033);
        validateResponse(responseLoginData);
        return responseLoginData.getSessionId();
    }

    private URL getWsdlUrl(URL cxServerUrl) {
        if (cxServerUrl.toString().endsWith("wsdl")) {
            return cxServerUrl;
        }

        try {
            return new URL(cxServerUrl, "/cxwebinterface/sdk/cxsdkwebservice.asmx?wsdl");
        } catch (MalformedURLException e) {
            return cxServerUrl;
        }
    }

    private void validateResponse(CxWSResponseLoginData responseLoginData) {
        if (responseLoginData == null || !responseLoginData.isIsSuccesfull())
            throw new RuntimeException("Login Failed");
    }
}
