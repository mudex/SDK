package com.cx.sdk.infrastructure;

import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.application.contracts.exceptions.NotAuthorizedException;
import com.cx.sdk.domain.enums.LoginType;
import com.cx.sdk.domain.exceptions.SdkException;
import com.cx.sdk.infrastructure.authentication.kerberos.WindowsAuthenticator;
import com.cx.sdk.infrastructure.proxy.ConnectionFactory;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import javax.ws.rs.core.NewCookie;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ehuds on 2/28/2017.
 */
public class CxRestClient {
    private final SDKConfigurationProvider sdkConfigurationProvider;
    private final RestResourcesURIBuilder restResourcesURIBuilder = new RestResourcesURIBuilder();
    private ConnectionFactory connectionFactory = null;
    private final Client client;
    public static final String AUTH_TYPE_NEGOTIATE = "Negotiate";
    private URL url = null;

    public CxRestClient(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
        URLConnectionClientHandler connection = getConnection();
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        client = new Client(connection, clientConfig);
    }

    private URLConnectionClientHandler getConnection() {
        setUrlByLoginType();
        URLConnectionClientHandler urlConnectionClientHandler = new URLConnectionClientHandler(new ConnectionFactory(sdkConfigurationProvider));
        return urlConnectionClientHandler;

    }

    private void setUrlByLoginType() {
        LoginType loginType = sdkConfigurationProvider.getLoginType();
        if (loginType.equals(LoginType.SSO)){
            url = restResourcesURIBuilder.buildSsoLoginURL(sdkConfigurationProvider.getCxServerUrl());
        } else if (loginType.equals(LoginType.CREDENTIALS)){
            url = restResourcesURIBuilder.buildLoginURL(sdkConfigurationProvider.getCxServerUrl());
        }
    }

    public Map<String, String> ssoLogin() throws Exception {

        ClientResponse response = baseRequest(url).post(ClientResponse.class, " ");

        validateResponse(response);

        return extractCxCookies(response);
    }

    public Map<String, String> login(String userName, String password) throws Exception {

        HashMap<String, Object> params = new HashMap();
        params.put("UserName", userName);
        params.put("Password", password);
        ClientResponse response = baseRequest(url)
                .type("application/json").accept("application/json")
                .post(ClientResponse.class, params);

        validateResponse(response);

        return extractCxCookies(response);
    }

    private WebResource.Builder baseRequest(URL resourceUrl) {
        WebResource webResource = client
                .resource(resourceUrl.toString());

        WebResource.Builder requestBuilder = webResource.header("CxOrigin", sdkConfigurationProvider.getCxOriginName());

        if (sdkConfigurationProvider.useKerberosAuthentication()) {
            requestBuilder = requestBuilder.header("Authorization", AUTH_TYPE_NEGOTIATE + " " + WindowsAuthenticator.getKrbToken(resourceUrl.getAuthority()));
        }

        return requestBuilder;
    }

    private void validateResponse(ClientResponse response) throws Exception {
        if (response.getStatus() == 401) {
            throw new NotAuthorizedException();
        }
        else if (response.getStatus() >= 400) {
            throw new SdkException("Failed : HTTP error code : "
                    + response.getStatus());
        }
    }

    private Map<String, String> extractCxCookies(ClientResponse response) {
        HashMap<String, String> coockies = new HashMap<>();
        for (NewCookie cookie : response.getCookies()) {
            if ("cxCookie".equals(cookie.getName()) || "CXCSRFToken".equals(cookie.getName()))
                coockies.put(cookie.getName(), cookie.getValue());
        }

        return coockies;
    }
}
