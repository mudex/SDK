package com.cx.sdk.application.services;

import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;

/**
 * Created by ehuds on 2/22/2017.
 */
public interface LoginService {
    Session login() throws SdkException;
    boolean isTokenExpired(Long expirationTime);
    Session getAccessTokenFromRefreshToken(String refreshToken);
    boolean isCxWebServiceAvailable();

}
