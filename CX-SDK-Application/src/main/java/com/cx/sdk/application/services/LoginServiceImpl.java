package com.cx.sdk.application.services;

import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.domain.CredentialsInputValidator;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by ehuds on 2/22/2017.
 */
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    public static final String FAILED_TO_LOGIN = "Failed to login";

    private final LoginProvider loginProvider;
    private final CredentialsInputValidator credentialsValidator;

    @Inject
    public LoginServiceImpl(LoginProvider loginProvider, CredentialsInputValidator credentialsValidator) {
        this.loginProvider = loginProvider;
        this.credentialsValidator = credentialsValidator;
    }

    @Override
    public Session login(String userName, String password) throws SdkException {
        Session session;
        try {
            credentialsValidator.validate(userName, password);
            session = loginProvider.login(userName, password);
        }
        catch(SdkException sdkException) {
            logger.info(FAILED_TO_LOGIN, sdkException);
            throw sdkException;
        }
        return session;
    }

    @Override
    public Session ssoLogin() throws SdkException {
        Session session;
        try {
            session = loginProvider.ssoLogin();
        }
        catch(SdkException sdkException) {
            logger.info(FAILED_TO_LOGIN, sdkException);
            throw sdkException;
        }
        return session;
    }

    @Override
    public Session samlLogin() throws SdkException {
        Session session;
        try {
            session = loginProvider.samlLogin();
        }
        catch(SdkException sdkException) {
            logger.info(FAILED_TO_LOGIN, sdkException);
            throw sdkException;
        }
        return session;
    }
}
