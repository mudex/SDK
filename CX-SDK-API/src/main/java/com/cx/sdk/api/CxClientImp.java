package com.cx.sdk.api;

import com.cx.sdk.DTOs.SessionDTO;
import com.cx.sdk.application.login.services.LoginService;
import com.cx.sdk.application.login.services.LoginServiceImp;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by ehuds on 2/22/2017.
 */
public class CxClientImp implements CxClient {
    private LoginService loginService;

    public static CxClient createNewInstance(SdkConfiguration configuration) {
        Injector injector = Guice.createInjector(new Bootstrapper(configuration));
        CxClient client = injector.getInstance(CxClient.class);
        return client;
    }

    public CxClientImp(LoginService loginService) {
        this.loginService = loginService;
    }

    public SessionDTO login(String userName, String password) {
        return new SessionDTO();
    }
}
