package com.cx.sdk.infrastructure.authentication.kerberos;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.auth.HttpAuthHeader;
import org.apache.cxf.transport.http.auth.SpnegoAuthSupplier;

import java.net.URI;

/**
 * Created by ehuds on 24/05/2017.
 */
public class DynamicAuthSupplier extends SpnegoAuthSupplier {

    private static boolean isKerberosActive = false;

    public static void setKerberosActive(boolean isActive){
        isKerberosActive = isActive;
    }

    @Override
    public String getAuthorization(AuthorizationPolicy authPolicy, URI currentURI, Message message, String fullHeader) {
        if(!isKerberosActive){
            return null;
        }

        String token = WindowsAuthenticator.getKrbToken(currentURI.getAuthority());
        return  HttpAuthHeader.AUTH_TYPE_NEGOTIATE + " " + token;
    }
}
