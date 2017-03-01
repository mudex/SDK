package com.cx.sdk.infrastructure;

import com.cx.sdk.application.contracts.SDKConfigurationProvider;

import java.net.URL;

/**
 * Created by ehuds on 2/28/2017.
 */
public class SDKConfigurationProviderFactory {
    public SDKConfigurationProvider create(URL serverUrl) {
        return initSdkConfigurationProvider(serverUrl, null);
    }
    public SDKConfigurationProvider create(URL serverUrl, String cxOrigin) {
     return initSdkConfigurationProvider(serverUrl, cxOrigin);
    }

    private SDKConfigurationProvider initSdkConfigurationProvider(URL serverUrl, String cxOrigin) {
        return new SDKConfigurationProvider() {
            @Override
            public String getCxOriginName() {
                return cxOrigin;
            }

            @Override
            public URL getCxServerUrl() {
                return serverUrl;
            }
        };
    }
}
