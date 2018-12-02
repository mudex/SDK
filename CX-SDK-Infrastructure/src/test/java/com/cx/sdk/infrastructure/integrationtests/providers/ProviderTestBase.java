package com.cx.sdk.infrastructure.integrationtests.providers;

import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.infrastructure.SDKConfigurationProviderFactory;
import com.cx.sdk.infrastructure.providers.LoginProviderImpl;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by victork on 22/03/2017.
 */
public abstract class ProviderTestBase {

    private static final String BASE_URL = "http://10.31.2.118";
    protected SDKConfigurationProvider sdkConfigurationProvider;

    protected ProviderTestBase() {
        URL serverUrl = getUrl(BASE_URL);
        sdkConfigurationProvider = new SDKConfigurationProviderFactory().create(serverUrl, null);
    }

    protected Session createValidSession() throws Exception {
        String userName = "admin@cx";
        String password = "Cx123456!";
        LoginProvider loginProvider = new LoginProviderImpl(sdkConfigurationProvider);
        Session session = loginProvider.login();
        return session;
    }
    
    protected Session createInvalidSession() {
        Session session = new Session("some-fake-id", null, null, null, false, false, false);
        return session;
    }

    protected URL getUrl(String urlAddress) {
        URL url;
        try {
            url = new URL(urlAddress);
        } catch (MalformedURLException e) {
            url = null;
        }
        return url;
    }
}
