package com.cx.sdk.IntegrationTests.application.login.services;

import com.cx.sdk.IntegrationTests.login.Session;
import com.cx.sdk.providers.CredentialsValidator;
import com.cx.sdk.IntegrationTests.providers.LoginProvider;

/**
 * Created by ehuds on 2/22/2017.
 */
public class LoginServiceImp implements LoginService {
    LoginProvider loginProvider;
    CredentialsValidator credentialsValidator;

    public LoginServiceImp(LoginProvider loginProvider, CredentialsValidator credentialsValidator) {
        this.loginProvider = loginProvider;
        this.credentialsValidator = credentialsValidator;
    }

    @Override
    public Session login(String userName, String password) {
        credentialsValidator.validate(userName, password);
        return loginProvider.login(userName, password);
    }
}
