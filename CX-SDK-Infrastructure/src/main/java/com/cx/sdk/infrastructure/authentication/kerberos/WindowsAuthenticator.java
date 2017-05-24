package com.cx.sdk.infrastructure.authentication;

import waffle.util.Base64;
import waffle.windows.auth.impl.WindowsSecurityContextImpl;

/**
 * Created by ehuds on 21/05/2017.
 */
public class WindowsAuthenticator {
    public static final String securityPackage = "Negotiate";
    public static String getKrbToken(String aTargetSPName) {
        if(null == aTargetSPName || aTargetSPName.trim().isEmpty()){
            return null;
        }
        return Base64.encode(WindowsSecurityContextImpl.getCurrent(securityPackage, aTargetSPName).getToken());
    }

    private WindowsAuthenticator(){
        super();
    }
}
