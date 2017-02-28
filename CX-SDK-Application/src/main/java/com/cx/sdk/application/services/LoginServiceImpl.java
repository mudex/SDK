package com.cx.sdk.application.services;

import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.domain.CredentialsValidator;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;

/**
 * Created by ehuds on 2/22/2017.
 */
public class LoginServiceImpl implements LoginService {
    LoginProvider loginProvider;
    CredentialsValidator credentialsValidator;

    public LoginServiceImpl(LoginProvider loginProvider, CredentialsValidator credentialsValidator) {
        this.loginProvider = loginProvider;
        this.credentialsValidator = credentialsValidator;
    }

    @Override
    public Session login(String userName, String password) throws SdkException {
        try {
            credentialsValidator.validate(userName, password);
            return loginProvider.login(userName, password);
        }
        catch(SdkException sdkException) {
            //TODO: log exception
            throw sdkException;
        }
        catch (Exception ex) {
            //TODO: log exception
            throw new SdkException("");
        }
    }
}
