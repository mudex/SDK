package com.cx.sdk.oidcLogin.api;

import com.cx.sdk.oidcLogin.exceptions.CxRestClientException;
import com.cx.sdk.oidcLogin.exceptions.CxRestLoginException;
import com.cx.sdk.oidcLogin.exceptions.CxValidateResponseException;
import com.cx.sdk.oidcLogin.restClient.entities.Permissions;
import com.cx.sdk.oidcLogin.webBrowsing.LoginData;

public interface CxOIDCLoginClient {

    LoginData login() throws Exception;

    boolean isTokenExpired(Long expirationTime);

    LoginData getAccessTokenFromRefreshToken(String accessToken) throws CxRestClientException, CxRestLoginException, CxValidateResponseException;

    Permissions getPermissions(String accessToken) throws CxValidateResponseException;
}
