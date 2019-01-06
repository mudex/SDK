package com.cx.sdk.infrastructure;

import com.checkmarx.v7.*;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.application.contracts.exceptions.NotAuthorizedException;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.ProxyParams;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.transports.http.configuration.ProxyServerType;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.*;


/**
 * Created by ehuds on 2/28/2017.
 */
public class CxSoapClient {
    private final SDKConfigurationProvider sdkConfigurationProvider;

    public CxSoapClient(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
    }

    public CxWSResponsePresetList getPresets(Session session) throws Exception {
        CxSDKWebServiceSoap cxSDKWebServiceSoap = createProxy(session);
        CxWSResponsePresetList response = cxSDKWebServiceSoap.getPresetList(session.getSessionId());
        validateResponse(response);
        return response;
    }

    public CxWSResponseGroupList getTeams(Session session) throws Exception {
        CxSDKWebServiceSoap cxSDKWebServiceSoap = createProxy(session);
        CxWSResponseGroupList response = cxSDKWebServiceSoap.getAssociatedGroupsList(session.getSessionId());
        validateResponse(response);
        return response;
    }

    public CxWSResponseConfigSetList getConfigurations(Session session) throws Exception {
        CxSDKWebServiceSoap cxSDKWebServiceSoap = createProxy(session);
        CxWSResponseConfigSetList response = cxSDKWebServiceSoap.getConfigurationSetList(session.getSessionId());
        validateResponse(response);
        return response;
    }

    public Boolean isProjectNameValid(Session session, String projectName, String groupId) throws Exception {
        CxSDKWebServiceSoap cxSDKWebServiceSoap = createProxy(session);
        CxWSBasicRepsonse response = cxSDKWebServiceSoap.isValidProjectName(session.getSessionId(), projectName, groupId);
        validateResponse(response);
        Boolean isValid = response.isIsSuccesfull();
        return isValid;
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

    private void validateResponse(CxWSBasicRepsonse response) throws Exception {
        if (response.isIsSuccesfull())
            return;

        if ("ReConnect".equals(response.getErrorMessage()))
            throw new NotAuthorizedException();

        throw new Exception(response.getErrorMessage());
    }

    private CxSDKWebServiceSoap createProxy(Session session) {
        URL wsdlUrl = getWsdlUrl(sdkConfigurationProvider.getCxServerUrl());
        CxSDKWebService cxSDKWebService = new CxSDKWebService(wsdlUrl);
        CxSDKWebServiceSoap cxSDKWebServiceSoap = cxSDKWebService.getCxSDKWebServiceSoap();
        setProxySettingsForSoap(cxSDKWebServiceSoap);
        setAuthorizationHeader(cxSDKWebServiceSoap, session);
        return cxSDKWebServiceSoap;
    }

    private void setProxySettingsForSoap(CxSDKWebServiceSoap cxSDKWebServiceSoap) {
        ProxyParams proxyParams = sdkConfigurationProvider.getProxyParams();
        if(proxyParams != null && proxyParams.getType() != null){
            Client client = ClientProxy.getClient(cxSDKWebServiceSoap);
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            String proxyServer = proxyParams.getServer();
            int proxyServerPort = proxyParams.getPort();
            if(proxyParams.getType().equals("HTTPS")){
                proxyParams.setType(Proxy.Type.HTTP.name());
            }
            ProxyServerType proxyServerType = ProxyServerType.valueOf(proxyParams.getType());
            HTTPClientPolicy clientPolicy = new HTTPClientPolicy();
            clientPolicy.setProxyServerType(proxyServerType);
            clientPolicy.setProxyServer(proxyServer);
            clientPolicy.setProxyServerPort(proxyServerPort);
            if(proxyParams.getUsername() != null){
                conduit.getProxyAuthorization().setUserName(proxyParams.getUsername());
                conduit.getProxyAuthorization().setPassword(proxyParams.getPassword());
            }
            conduit.setClient(clientPolicy);
        }
    }

    private void setAuthorizationHeader(CxSDKWebServiceSoap cxSDKWebServiceSoap, Session session) {
        Client client = ClientProxy.getClient(cxSDKWebServiceSoap);
        AuthorizationHeaderInterceptor interceptor = new AuthorizationHeaderInterceptor(session.getAccessToken());
        client.getOutInterceptors().add(interceptor);
    }
}
