package com.cx.sdk.application.com.cx.sdk.application.login.services;

import com.cx.sdk.login.Authentication;

/**
 * Created by ehuds on 2/22/2017.
 */
public class LoginServiceImp implements LoginService {

    @Override
    public Authentication login(String userName, String password) {
        validateInput(userName, password);
        return new Authentication();
    }

    private void validateInput(String userName, String password) {
        if (userName.isEmpty() || password.isEmpty())
            throw new IllegalArgumentException();
    }
}
