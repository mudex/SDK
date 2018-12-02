package com.cx.sdk.infrastructure.providers;

import com.checkmarx.plugin.common.api.CxOIDCLoginClient;
import com.checkmarx.plugin.common.api.CxOIDCLoginClientImpl;
import com.checkmarx.plugin.common.webBrowsing.LoginData;
import com.checkmarx.v7.CxWSResponseLoginData;
import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;
import com.cx.sdk.infrastructure.CxSoapClient;
import com.cx.sdk.infrastructure.proxy.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.net.*;

/**
 * Created by ehuds on 2/25/2017.
 */
public class LoginProviderImpl implements LoginProvider {

    private final SDKConfigurationProvider sdkConfigurationProvider;
    private final Logger logger = LoggerFactory.getLogger(LoginProviderImpl.class);
    private final ConnectionFactory connectionFactory;

    public static final String SERVER_CONNECTIVITY_FAILURE = "Failed to validate server connectivity for server: ";
    public static final String CX_SDK_WEB_SERVICE_URL = "/cxwebinterface/sdk/cxsdkwebservice.asmx";

    @Inject
    public LoginProviderImpl(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
        connectionFactory = new ConnectionFactory(sdkConfigurationProvider);
    }



    @Override
    public Session login() throws SdkException {
        CxOIDCLoginClient cxOIDCLoginClient = new CxOIDCLoginClientImpl(sdkConfigurationProvider.getCxServerUrl(),
                sdkConfigurationProvider.getCxOriginName());

        if (!isCxServerAvailable()) {
            throw new SdkException(SERVER_CONNECTIVITY_FAILURE + sdkConfigurationProvider.getCxServerUrl().toString());
        }

        LoginData loginData = null;
        try {
            loginData = cxOIDCLoginClient.login();
        } catch (Exception e) {
            String errorMessage = String.format("Failed to preform saml login to server: %s",
                    sdkConfigurationProvider.getCxServerUrl().toString());
            logger.error(errorMessage, e);
            throw new SdkException(errorMessage, e);
        }

        if (loginData.wasCanceled() )
            return null;

        return new Session("",
                loginData.getAccessToken(),
                loginData.getRefreshToken(),
                loginData.getAccessTokenExpirationInMillis(),
                true,
                true,
                true);
    }

    private boolean isCxServerAvailable() {
        return isCxWebServiceAvailable();
    }

    private boolean isCxWebServiceAvailable() {
        int responseCode;
        try {
            URL urlAddress = new URL(sdkConfigurationProvider.getCxServerUrl(), CX_SDK_WEB_SERVICE_URL);
            HttpURLConnection httpConnection = connectionFactory.getHttpURLConnection(urlAddress);
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            responseCode = httpConnection.getResponseCode();
        } catch (Exception e) {
            logger.error("Cx server interface is not available", e);
            return false;
        }

        return (responseCode != 404);
    }
}
