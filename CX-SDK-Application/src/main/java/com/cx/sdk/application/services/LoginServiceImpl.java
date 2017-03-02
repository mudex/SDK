package com.cx.sdk.application.services;

import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.domain.CredentialsValidator;
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

    private final LoginProvider loginProvider;
    private final CredentialsValidator credentialsValidator;

    @Inject
    public LoginServiceImpl(LoginProvider loginProvider, CredentialsValidator credentialsValidator) {
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
            logger.info("Failed to login", sdkException);
            throw sdkException;
        }
        return session;
    }
}
