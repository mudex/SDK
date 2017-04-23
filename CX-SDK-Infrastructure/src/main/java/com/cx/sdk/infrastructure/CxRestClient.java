package com.cx.sdk.infrastructure;

import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.application.contracts.exceptions.NotAuthorizedException;
import com.cx.sdk.domain.exceptions.SdkException;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.ws.rs.core.NewCookie;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ehuds on 2/28/2017.
 */
public class CxRestClient {
    private final SDKConfigurationProvider sdkConfigurationProvider;
    private final RestResourcesURIBuilder restResourcesURIBuilder = new RestResourcesURIBuilder();
    private final Client client;

    public CxRestClient(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        client = Client.create(clientConfig);
    }

    public Map<String, String> ssoLogin() throws Exception {

            WebResource webResource = client
                    .resource(restResourcesURIBuilder.buildSsoLoginURL(sdkConfigurationProvider.getCxServerUrl()).toString());

            ClientResponse response = webResource.header("CxOrigin",sdkConfigurationProvider.getCxOriginName())
                    .post(ClientResponse.class, " ");

        validateResponse(response);

        return extractCxCookies(response);
    }

    public Map<String, String> login(String userName, String password) throws Exception {

        WebResource webResource = client
                .resource(restResourcesURIBuilder.buildLoginURL(sdkConfigurationProvider.getCxServerUrl()).toString());

        HashMap<String, Object> params = new HashMap();
        params.put("UserName", userName);
        params.put("Password", password);
        ClientResponse response = webResource.accept("application/json")
                .type("application/json")
                .header("CxOrigin",sdkConfigurationProvider.getCxOriginName())
                .post(ClientResponse.class, params);

        validateResponse(response);

        return extractCxCookies(response);
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
