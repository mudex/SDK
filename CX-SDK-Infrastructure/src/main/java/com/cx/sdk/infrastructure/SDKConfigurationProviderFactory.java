package com.cx.sdk.infrastructure;

import com.cx.sdk.application.contracts.SDKConfigurationProvider;

import java.net.URL;

/**
 * Created by ehuds on 2/28/2017.
 */
public class SDKConfigurationProviderFactory {
    public SDKConfigurationProvider create(URL serverUrl) {
     return initSdkConfigurationProvider(serverUrl);
    }

    private SDKConfigurationProvider initSdkConfigurationProvider(URL serverUrl) {
        return new SDKConfigurationProvider() {
            @Override
            public String getOriginName() {
                return null;
            }

            @Override
            public void setOriginName(String originName) {

            }

            @Override
            public URL getCxServerUrl() {
                return serverUrl;
            }
        };
    }
}
