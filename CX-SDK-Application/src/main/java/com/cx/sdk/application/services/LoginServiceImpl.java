package com.cx.sdk.application.services;

import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.domain.CredentialsInputValidator;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.enums.LoginType;
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
    public static final String FAILED_TO_LOGIN_WITH_CREDENTIALS = "Failed to login with credentials";
    public static final String FAILED_TO_LOGIN_WITH_SSO = "Failed to login with SSO";
    public static final String FAILED_TO_LOGIN_WITH_SAML = "Failed to login with SAML";

    private final LoginProvider loginProvider;
    private final CredentialsInputValidator credentialsValidator;
    private final SDKConfigurationProvider sdkConfigurationProvider;

    @Inject
    public LoginServiceImpl(LoginProvider loginProvider, CredentialsInputValidator credentialsValidator, SDKConfigurationProvider sdkConfigurationProvider) {
        this.loginProvider = loginProvider;
        this.credentialsValidator = credentialsValidator;
        this.sdkConfigurationProvider = sdkConfigurationProvider;
    }

    @Override
    public Session login() throws SdkException {
        String userName = sdkConfigurationProvider.getUsername();
        String password = sdkConfigurationProvider.getPassword();

        Session session;
        try {
            validateLoginConfiguration(LoginType.CREDENTIALS);
            credentialsValidator.validate(userName, password);
            session = loginProvider.login(userName, password);
        }
        catch(SdkException sdkException) {
            logger.info(FAILED_TO_LOGIN_WITH_CREDENTIALS, sdkException);
            throw sdkException;
        }
        catch(Exception e) {
            logger.error(FAILED_TO_LOGIN_WITH_CREDENTIALS, e);
            throw new SdkException("Failed to login with credentials", e);
        }
        return session;
    }

    @Override
    public Session ssoLogin() throws SdkException {
        Session session;
        try {
            validateLoginConfiguration(LoginType.SSO);
            session = loginProvider.ssoLogin();
        }
        catch(SdkException sdkException) {
            logger.warn(FAILED_TO_LOGIN_WITH_SSO, sdkException);
            throw sdkException;
        }
        catch(Exception e) {
            logger.error(FAILED_TO_LOGIN_WITH_SSO, e);
            throw new RuntimeException(e);
        }
        return session;
    }

    @Override
    public Session samlLogin() throws SdkException {
        Session session;
        try {
            validateLoginConfiguration(LoginType.SAML);
            session = loginProvider.samlLogin();
        }
        catch(SdkException sdkException) {
            logger.warn(FAILED_TO_LOGIN_WITH_SAML, sdkException);
            throw sdkException;
        }
        catch(Exception e) {
            logger.error(FAILED_TO_LOGIN_WITH_SAML, e);
            throw new SdkException("Failed to login with SAML", e);
        }
        return session;
    }

    private void validateLoginConfiguration(LoginType expectedLoginType) throws SdkException {
        boolean hasExpectedLoginType = expectedLoginType == sdkConfigurationProvider.getLoginType();
        if (hasExpectedLoginType)
            return;
        String errorMessage = String.format("Login failed. Please make sure the requested login (%s) conforms to the configured login type (%s)", expectedLoginType, sdkConfigurationProvider.getLoginType());
        throw new SdkException(errorMessage);
    }
}
