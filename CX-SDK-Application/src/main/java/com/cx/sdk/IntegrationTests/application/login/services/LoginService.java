package com.cx.sdk.IntegrationTests.application.login.services;

import com.cx.sdk.IntegrationTests.login.Session;

/**
 * Created by ehuds on 2/22/2017.
 */
public interface LoginService {
    Session login(String userName, String password);
}
