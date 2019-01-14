package com.cx.sdk.api;

import com.cx.sdk.api.dtos.EngineConfigurationDTO;
import com.cx.sdk.api.dtos.PresetDTO;
import com.cx.sdk.api.dtos.SessionDTO;
import com.cx.sdk.api.dtos.TeamDTO;
import com.cx.sdk.application.contracts.exceptions.NotAuthorizedException;
import com.cx.sdk.application.contracts.providers.*;
import com.cx.sdk.application.services.LoginService;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.EngineConfiguration;
import com.cx.sdk.domain.entities.Preset;
import com.cx.sdk.domain.entities.Team;
import com.cx.sdk.domain.exceptions.SdkException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ehuds on 2/22/2017.
 */
public class CxClientImpl implements CxClient {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static final String LOGIN_CONF_PATH = "resources/login.conf";

    private final LoginService loginService;
    private final SDKConfigurationProvider sdkConfigurationProvider;
    private static Session singletonSession;

    @Inject
    private CxClientImpl(LoginService loginService,
                         SDKConfigurationProvider sdkConfigurationProvider) {
        this.loginService = loginService;
        this.sdkConfigurationProvider = sdkConfigurationProvider;
    }

    public static CxClient createNewInstance(SdkConfiguration configuration) {
        if (configuration.getCxServerUrl() == null) {
            throw new IllegalArgumentException("Please provide the CxServerURL");
        }

        initKerberosParameters(configuration);

        singletonSession = null;
        Injector injector = Guice.createInjector(new Bootstrapper(configuration));
        return injector.getInstance(CxClient.class);
    }

    private static void initKerberosParameters(SdkConfiguration configuration) {
        if (configuration.useKerberosAuthentication()) {
            System.setProperty("java.security.auth.login.config", new File(LOGIN_CONF_PATH).getAbsolutePath());
            System.setProperty("com.sun.security.auth.module.Krb5LoginModule", "true");
        }
    }

    @Override
    public SessionDTO login() throws SdkException {
        singletonSession = loginService.login();
        return modelMapper.map(singletonSession, SessionDTO.class);
    }


    private void setSingletonSession(SessionDTO sessionDTO) {
        if(singletonSession == null && sessionDTO != null) {
            singletonSession = new Session(sessionDTO.getSessionId(),
                    sessionDTO.getAccessToken(),
                    sessionDTO.getRefreshToken(),
                    sessionDTO.getAccessTokenExpiration(),
                    sessionDTO.getIsScanner(),
                    sessionDTO.isAllowedToChangeNotExploitable(),
                    sessionDTO.isIsAllowedToModifyResultDetails());
        } else if (sessionDTO == null) {
            login();
        }
    }

    @Override
    public boolean isTokenExpired(Long expirationTime) {
        return loginService.isTokenExpired(expirationTime);
    }

    @Override
    public SessionDTO getAccessTokenFromRefreshToken(String refreshToken) {
        singletonSession  = loginService.getAccessTokenFromRefreshToken(refreshToken);
        return modelMapper.map(singletonSession, SessionDTO.class);
    }

}
