package com.cx.sdk.application.services;

import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;

/**
 * Created by ehuds on 2/22/2017.
 */
public interface LoginService {
    Session login(String userName, String password) throws SdkException;
    Session ssoLogin() throws SdkException;
    Session samlLogin() throws SdkException;
}
