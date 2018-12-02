package com.cx.sdk.application.services;

import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
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
    private final SDKConfigurationProvider sdkConfigurationProvider;

    @Inject
    public LoginServiceImpl(LoginProvider loginProvider, SDKConfigurationProvider sdkConfigurationProvider) {
        this.loginProvider = loginProvider;
        this.sdkConfigurationProvider = sdkConfigurationProvider;
    }

    @Override
    public Session login() throws SdkException {
        Session session;
        try {
            session = loginProvider.login();
        } catch (SdkException sdkException) {
            logger.info(FAILED_TO_LOGIN, sdkException);
            throw sdkException;
        } catch (Exception e) {
            logger.error(FAILED_TO_LOGIN, e);
            throw new SdkException(FAILED_TO_LOGIN, e);
        }
        return session;
    }

}
