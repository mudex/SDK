package com.cx.sdk.infrastructure;

import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.domain.entities.ProxyParams;
import com.cx.sdk.domain.enums.LoginType;

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
            String cxOrigin,
            LoginType loginType,
            String username,
            String password) {
        return initSdkConfigurationProvider(serverUrl, cxOrigin, loginType, username, password, false, null);
    }

    public SDKConfigurationProvider create(
            URL serverUrl,
            String cxOrigin,
            LoginType loginType,
            String username,
            String password,
            Boolean useKerberosAuthentication, ProxyParams proxyParams) {
        return initSdkConfigurationProvider(serverUrl, cxOrigin, loginType, username, password, useKerberosAuthentication, proxyParams);
    }

    private SDKConfigurationProvider initSdkConfigurationProvider(final URL serverUrl,final String cxOrigin,final LoginType loginType,final String username,final String password,
                                                                  final Boolean useKerberosAuthentication, final ProxyParams proxyParams ) {
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
            public LoginType getLoginType() {
                return loginType;
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String getPassword() {
                return password;
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
