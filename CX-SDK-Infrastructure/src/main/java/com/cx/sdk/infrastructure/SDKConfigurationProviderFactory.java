package com.cx.sdk.infrastructure;

import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.domain.entities.ProxyParams;

import java.net.URL;

/**
 * Created by ehuds on 2/28/2017.
 */
public class SDKConfigurationProviderFactory {
//    public SDKConfigurationProvider create(URL serverUrl) {
//        return initSdkConfigurationProvider(serverUrl, null);
//    }

    public SDKConfigurationProvider create(
            URL serverUrl,
            String cxOrigin) {
        return initSdkConfigurationProvider(serverUrl, cxOrigin, false, null);
    }

    public SDKConfigurationProvider create(
            URL serverUrl,
            String cxOrigin,
            Boolean useKerberosAuthentication, ProxyParams proxyParams) {
        return initSdkConfigurationProvider(serverUrl, cxOrigin, useKerberosAuthentication, proxyParams);
    }

    private SDKConfigurationProvider initSdkConfigurationProvider(final URL serverUrl,final String cxOrigin, final Boolean useKerberosAuthentication, final ProxyParams proxyParams ) {
        return new SDKConfigurationProvider() {
            @Override
            public String getCxOriginName() {
                return cxOrigin;
            }

            @Override
            public URL getCxServerUrl() {
                return serverUrl;
            }

            @Override
            public Boolean useKerberosAuthentication() {
                return useKerberosAuthentication;
            }

            @Override
            public ProxyParams getProxyParams() {
                return proxyParams;
            }
        };
    }
}
