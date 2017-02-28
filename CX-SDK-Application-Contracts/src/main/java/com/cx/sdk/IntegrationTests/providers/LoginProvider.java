package com.cx.sdk.IntegrationTests.providers;

import com.cx.sdk.IntegrationTests.login.Session;

/**
 * Created by ehuds on 2/25/2017.
 */
public interface LoginProvider {
    Session login(String userName, String password);
}
