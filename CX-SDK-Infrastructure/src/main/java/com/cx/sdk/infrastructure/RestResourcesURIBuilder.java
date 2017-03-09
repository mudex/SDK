package com.cx.sdk.infrastructure;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ehuds on 2/28/2017.
 */
public class RestResourcesURIBuilder {
    private static final String APPLICATION_NAME = "CxRestApi";

    protected URL buildLoginURL(URL serverUrl) {
        try {
            return new URL(serverUrl, APPLICATION_NAME + "/auth/login");
        } catch (MalformedURLException e) {
            return serverUrl;
        }
    }

    protected URL buildSsoLoginURL(URL serverUrl) {
        try {
            return new URL(serverUrl, APPLICATION_NAME + "/auth/ssologin");
        } catch (MalformedURLException e) {
            return serverUrl;
        }
    }
}
