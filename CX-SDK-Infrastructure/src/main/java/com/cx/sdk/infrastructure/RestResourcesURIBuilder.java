package com.cx.sdk.infrastructure;

import java.net.MalformedURLException;
import java.net.URL;

import static com.cx.sdk.oidcLogin.constants.Consts.APPLICATION_NAME;

/**
 * Created by ehuds on 2/28/2017.
 */
public class RestResourcesURIBuilder {
    private static final String IDENTITY_CONNECT_RESOURCE = "auth/identity/connect";
    private static final String TOKEN_LOGIN_RESOURCE = "token";
    public static final String AUTH_LOGIN = "/auth/login";
    public static final String AUTH_SSOLOGIN = "/auth/ssologin";

    protected URL buildLoginURL(URL serverUrl) {
        try {
            return new URL(serverUrl, APPLICATION_NAME + AUTH_LOGIN);
        } catch (MalformedURLException e) {
            return serverUrl;
        }
    }

    protected URL buildSsoLoginURL(URL serverUrl) {
        try {
            return new URL(serverUrl, APPLICATION_NAME + AUTH_SSOLOGIN);
        } catch (MalformedURLException e) {
            return serverUrl;
        }
    }

    public static URL getAccessTokenURL(URL serverUrl) {
        try {
            return new URL(serverUrl, APPLICATION_NAME + "/" + IDENTITY_CONNECT_RESOURCE + "/" + TOKEN_LOGIN_RESOURCE);
        } catch (MalformedURLException e) {
            return serverUrl;
        }
    }
}
