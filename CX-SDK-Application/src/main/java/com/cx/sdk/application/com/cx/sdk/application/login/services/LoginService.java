package com.cx.sdk.application.com.cx.sdk.application.login.services;

import com.cx.sdk.login.Authentication;

/**
 * Created by ehuds on 2/22/2017.
 */
public interface LoginService {
    Authentication login(String userName, String password);
}
