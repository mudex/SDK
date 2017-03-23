package com.cx.sdk.api;

import com.cx.sdk.api.dtos.SessionDTO;
import com.cx.sdk.application.contracts.exceptions.NotAuthorizedException;
import com.cx.sdk.application.services.LoginService;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.modelmapper.ModelMapper;
import javax.inject.Inject;
import java.util.function.Supplier;

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
        if (configuration.getLoginType() == null)
            throw new IllegalArgumentException("Please provide a LoginType");

        if (configuration.getCxServerUrl() == null) {
            throw new IllegalArgumentException("Please provide the CxServerURL");
        }

        Injector injector = Guice.createInjector(new Bootstrapper(configuration));
        return injector.getInstance(CxClient.class);
    }

    @Override
    public SessionDTO login() throws Exception {
        Session session = loginService.login();
        return modelMapper.map(session, SessionDTO.class);
    }

    @Override
    public SessionDTO ssoLogin() throws Exception {
        Session session = loginService.ssoLogin();
        return modelMapper.map(session, SessionDTO.class);
    }

    @Override
    public SessionDTO samlLogin() throws Exception {
        Session session = loginService.samlLogin();
        return modelMapper.map(session, SessionDTO.class);
    }

    private class HandleAuthorizationFailureCommand<T> {
        public T run(Supplier<T> function) throws Exception {
            try {
                return function.get();
            }
            catch(Exception e) {
                if (!e.getClass().equals(NotAuthorizedException.class))
                    throw e;

                ssoLogin();
                return function.get();
            }
        }
    }
}
