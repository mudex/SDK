package com.cx.sdk.infrastructure;

import com.checkmarx.v7.*;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.Preset;
import com.cx.sdk.domain.exceptions.SdkException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * Created by ehuds on 2/28/2017.
 */
public class CxSoapClient {
    private final SDKConfigurationProvider sdkConfigurationProvider;

    public CxSoapClient(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
    }

    public CxWSResponseLoginData login(String userName, String password) throws SdkException {
        URL wsdlUrl = getWsdlUrl(sdkConfigurationProvider.getCxServerUrl());
        CxSDKWebService cxSDKWebService = new CxSDKWebService(wsdlUrl);
        CxSDKWebServiceSoap cxSDKWebServiceSoap = cxSDKWebService.getCxSDKWebServiceSoap();
        Credentials credentials = new Credentials();
        credentials.setUser(userName);
        credentials.setPass(password);

        CxWSResponseLoginData responseLoginData = cxSDKWebServiceSoap.login(credentials, 1033);
        validateLoginResponse(responseLoginData, "Login failed");
        return responseLoginData;
    }

    public CxWSResponseLoginData ssoLogin() throws SdkException {
        CxSDKWebServiceSoap cxSDKWebServiceSoap = createProxy();

        CxWSResponseLoginData responseLoginData = cxSDKWebServiceSoap.ssoLogin(new Credentials(), 1033);
        validateLoginResponse(responseLoginData, "Login failed");
        return responseLoginData;
    }

    public CxWSResponsePresetList getPresets(Session session) throws Exception {
        CxSDKWebServiceSoap cxSDKWebServiceSoap = createProxy();
        CxWSResponsePresetList response = cxSDKWebServiceSoap.getPresetList(session.getSessionId());
        validateResponse(response, "Failed to get presets");
        return response;
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

    private void validateResponse(CxWSBasicRepsonse response, String errorMessage) throws Exception {
        if (response == null || !response.isIsSuccesfull())
            throw new Exception(response.getErrorMessage());
    }

    private void validateLoginResponse(CxWSBasicRepsonse response, String errorMessage) throws SdkException {
        if (response == null || !response.isIsSuccesfull())
            throw new SdkException(errorMessage);
    }

    private CxSDKWebServiceSoap createProxy() {
        URL wsdlUrl = getWsdlUrl(sdkConfigurationProvider.getCxServerUrl());
        CxSDKWebService cxSDKWebService = new CxSDKWebService(wsdlUrl);
        CxSDKWebServiceSoap cxSDKWebServiceSoap = cxSDKWebService.getCxSDKWebServiceSoap();
        return cxSDKWebServiceSoap;
    }
}
