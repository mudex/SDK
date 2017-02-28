package com.cx.sdk.infrastructure;

import com.cx.sdk.application.contracts.SDKConfigurationProvider;
import com.cx.sdk.domain.exceptions.SdkException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONObject;

import javax.ws.rs.core.NewCookie;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ehuds on 2/28/2017.
 */
public class CxRestClient {
    SDKConfigurationProvider sdkConfigurationProvider;
    RestResourcesURIBuilder restResourcesURIBuilder = new RestResourcesURIBuilder();
    Client client = Client.create();

    public CxRestClient(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
    }

    public Map<String, String> login(String userName, String password) throws SdkException {

            WebResource webResource = client
                    .resource(restResourcesURIBuilder.buildLoginURL(sdkConfigurationProvider.getCxServerUrl()).toString());

            HashMap<String, String> params = new HashMap();
            params.put("username", userName);
            params.put("password", password);

            ClientResponse response = webResource.type("application/json")
                    .post(ClientResponse.class, new JSONObject(params).toString());

        validateResponse(response);

        return extractCxCookies(response);
    }

    private void validateResponse(ClientResponse response) throws SdkException {
        if (response.getStatus() >= 400) {
            throw new SdkException("Failed : HTTP error code : "
                    + response.getStatus());
        }
    }

    private Map<String, String> extractCxCookies(ClientResponse response) {
        HashMap<String, String> coockies = new HashMap<>();
        for (NewCookie coockie : response.getCookies()) {
            if ("cxCookie".equals(coockie.getName()) || "CXCSRFToken".equals(coockie.getName()))
                coockies.put(coockie.getName(), coockie.getValue());
        }

        return coockies;
    }
}
