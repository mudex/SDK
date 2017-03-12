package com.cx.sdk.api;

import com.cx.sdk.api.dtos.SessionDTO;
import com.cx.sdk.application.services.LoginService;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.modelmapper.ModelMapper;
import javax.inject.Inject;

/**
 * Created by ehuds on 2/22/2017.
 */
public class CxClientImpl implements CxClient {
    private static final ModelMapper modelMapper = new ModelMapper();

    private final LoginService loginService;

    @Inject
    private CxClientImpl(LoginService loginService) {
        this.loginService = loginService;
    }

    public static CxClient createNewInstance(SdkConfiguration configuration) {
        Injector injector = Guice.createInjector(new Bootstrapper(configuration));
        return injector.getInstance(CxClient.class);
    }

    @Override
    public SessionDTO login(String userName, String password) throws SdkException {
        Session session = loginService.login(userName, password);
        return modelMapper.map(session, SessionDTO.class);
    }

    @Override
    public SessionDTO ssoLogin() throws SdkException {
        Session session = loginService.ssoLogin();
        return modelMapper.map(session, SessionDTO.class);
    }

    @Override
    public SessionDTO samlLogin() throws SdkException {
        Session session = loginService.samlLogin();
        return modelMapper.map(session, SessionDTO.class);
    }
}
