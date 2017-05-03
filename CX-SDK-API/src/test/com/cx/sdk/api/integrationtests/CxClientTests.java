package com.cx.sdk.api.integrationtests;

import com.cx.sdk.api.CxClient;
import com.cx.sdk.api.CxClientImpl;
import com.cx.sdk.api.SdkConfiguration;
import com.cx.sdk.api.dtos.LoginTypeDTO;
import com.cx.sdk.api.dtos.SessionDTO;
import com.cx.sdk.domain.exceptions.SdkException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ehuds on 3/1/2017.
 */
@Ignore
public class CxClientTests {
    @Test
    public void login_validCradentials_loginSuccessfull() throws Exception {
        CxClient cxClient = CxClientImpl.createNewInstance(new SdkConfiguration(new URL("http://10.31.2.118"), "Intelij", LoginTypeDTO.CREDENTIALS, "admin@cx", "Cx123456!"));
        SessionDTO session = cxClient.login();
        Assert.assertNotNull(session);
    }

    @Test
    public void ssoLogin_domainUserConfigured_loginSuccessfull() throws Exception {
        CxClient cxClient = CxClientImpl.createNewInstance(new SdkConfiguration(new URL("http://10.31.2.118"), "Intelij", LoginTypeDTO.CREDENTIALS, "admin@cx", "Cx123456!"));
        SessionDTO session = cxClient.login();
        Assert.assertNotNull(session);
    }

    @Test
    public void samlLogin_samlConfiguredProperly_loginSuccessful() throws Exception {
        CxClient cxClient = CxClientImpl.createNewInstance(new SdkConfiguration(new URL("http://10.31.2.118"), "cx-Intelij", LoginTypeDTO.CREDENTIALS, "admin@cx", "Cx123456!"));
        SessionDTO session = cxClient.login();
        Assert.assertNotNull(session);
    }
}
