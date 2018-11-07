package com.cx.sdk.api;

import com.cx.sdk.api.dtos.LoginTypeDTO;
import com.cx.sdk.domain.entities.ProxyParams;

import java.net.URL;

/**
 * Created by victork on 28/02/2017.
 */
public class SdkConfiguration {
    private final URL cxServerUrl;
    private final String cxOrigin;
    private final LoginTypeDTO loginType;
    private final String username;
    private final String password;
    private final Boolean useKerberosAuthentication;
    private final ProxyParams proxyParams;


    public SdkConfiguration(URL cxServerUrl, String cxOrigin, LoginTypeDTO loginType, String username, String password, ProxyParams proxyParams)
    {
        this(cxServerUrl, cxOrigin, loginType, username, password, false, proxyParams);
    }

    public SdkConfiguration(URL cxServerUrl, String cxOrigin, LoginTypeDTO loginType, String username, String password, Boolean useKerberosAuthentication, ProxyParams proxyParams)
    {
        this.cxServerUrl = cxServerUrl;
        this.cxOrigin = cxOrigin;
        this.loginType = loginType;
        this.username = username;
        this.password = password;
        this.useKerberosAuthentication = useKerberosAuthentication;
        this.proxyParams = proxyParams;
    }

    public String getOriginName() {
        return cxOrigin;
    }

    public URL getCxServerUrl() {
        return cxServerUrl;
    }

    public LoginTypeDTO getLoginType() {
        return loginType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean useKerberosAuthentication() { return useKerberosAuthentication; }

    public ProxyParams getProxyParams() {
        return proxyParams;
    }
}
