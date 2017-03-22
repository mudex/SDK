package com.cx.sdk.infrastructure.integrationtests.providers;

import com.checkmarx.v7.CxWSResponseLoginData;
import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;
import com.cx.sdk.infrastructure.CxSoapClient;
import com.cx.sdk.infrastructure.SDKConfigurationProviderFactory;
import com.cx.sdk.infrastructure.providers.LoginProviderImpl;
import com.sun.org.apache.xerces.internal.util.URI;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by victork on 22/03/2017.
 */
public abstract class ProviderTestBase {
    private static final String BASE_URL = "http://10.31.2.118";
    protected SDKConfigurationProvider sdkConfigurationProvider;

    protected ProviderTestBase() {
        URL serverUrl = getUrl();
        sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(serverUrl);
    }

    protected Session createSession() throws Exception {
        String userName = "admin@cx";
        String password = "Cx123456!";
        LoginProvider loginProvider = new LoginProviderImpl(sdkConfigurationProvider);
        Session session = loginProvider.login(userName, password);
        return session;
    }

    private URL getUrl() {
        URL url;
        try {
            url = new URL(BASE_URL);
        } catch (MalformedURLException e) {
            url = null;
        }
        return url;
    }
}
