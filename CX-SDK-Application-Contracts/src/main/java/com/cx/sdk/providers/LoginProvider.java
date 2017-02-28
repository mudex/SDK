package com.cx.sdk.providers;

import com.cx.sdk.login.Session;

/**
 * Created by ehuds on 2/25/2017.
 */
public interface LoginProvider {
    Session login(String userName, String password);
}
