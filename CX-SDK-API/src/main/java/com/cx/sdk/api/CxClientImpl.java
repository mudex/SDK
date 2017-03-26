package com.cx.sdk.api;

import com.cx.sdk.api.dtos.EngineConfigurationDTO;
import com.cx.sdk.api.dtos.PresetDTO;
import com.cx.sdk.api.dtos.SessionDTO;
import com.cx.sdk.api.dtos.TeamDTO;
import com.cx.sdk.application.contracts.exceptions.NotAuthorizedException;
import com.cx.sdk.application.contracts.providers.ConfigurationProvider;
import com.cx.sdk.application.contracts.providers.PresetProvider;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.application.contracts.providers.TeamProvider;
import com.cx.sdk.application.services.LoginService;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.EngineConfiguration;
import com.cx.sdk.domain.entities.Preset;
import com.cx.sdk.domain.entities.Team;
import com.cx.sdk.domain.enums.LoginType;
import com.cx.sdk.domain.exceptions.SdkException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.modelmapper.ModelMapper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by ehuds on 2/22/2017.
 */
public class CxClientImpl implements CxClient {
    private static final ModelMapper modelMapper = new ModelMapper();

    private final LoginService loginService;
    private final SDKConfigurationProvider sdkConfigurationProvider;
    private final ConfigurationProvider configurationProvider;
    private final PresetProvider presetProvider;
    private final TeamProvider teamProvider;

    private Session session;

    @Inject
    private CxClientImpl(LoginService loginService,
                         SDKConfigurationProvider sdkConfigurationProvider,
                         ConfigurationProvider configurationProvider,
                         PresetProvider presetProvider,
                         TeamProvider teamProvider) {
        this.loginService = loginService;
        this.sdkConfigurationProvider = sdkConfigurationProvider;
        this.configurationProvider = configurationProvider;
        this.presetProvider = presetProvider;
        this.teamProvider = teamProvider;
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
        session = loginService.login();
        return modelMapper.map(session, SessionDTO.class);
    }

    @Override
    public SessionDTO ssoLogin() throws Exception {
        session = loginService.ssoLogin();
        return modelMapper.map(session, SessionDTO.class);
    }

    @Override
    public SessionDTO samlLogin() throws Exception {
        session = loginService.samlLogin();
        return modelMapper.map(session, SessionDTO.class);
    }

    @Override
    public List<EngineConfigurationDTO> getEngineConfigurations() throws Exception {
        HandleAuthorizationFailureCommand<List<EngineConfiguration>> command = new HandleAuthorizationFailureCommand<>();
        List<EngineConfiguration> engineConfigurations = command.run(() -> configurationProvider.getEngineConfigurations(session));
        List<EngineConfigurationDTO> dtos = engineConfigurations.stream()
                .map(engineConfiguration -> modelMapper.map(engineConfiguration, EngineConfigurationDTO.class))
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<PresetDTO> getPresets() throws Exception {
        HandleAuthorizationFailureCommand<List<Preset>> command = new HandleAuthorizationFailureCommand<>();
        List<Preset> presets = command.run(() -> presetProvider.getPresets(session));
        List<PresetDTO> dtos = presets.stream()
                .map(preset -> modelMapper.map(preset, PresetDTO.class))
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<TeamDTO> getTeams() throws Exception {
        HandleAuthorizationFailureCommand<List<Team>> command = new HandleAuthorizationFailureCommand<>();
        List<Team> teams = command.run(() -> teamProvider.getTeams(session));
        List<TeamDTO> dtos = teams.stream()
                .map(team -> modelMapper.map(team, TeamDTO.class))
                .collect(Collectors.toList());
        return dtos;
    }

    public class HandleAuthorizationFailureCommand<T> {
        public T run(Supplier<T> function) throws Exception {
            try {
                if (session == null) {
                    handleLogin();
                }
                return function.get();
            }
            catch(Exception e) {
                boolean hasAuthorizationError = e.getClass().equals(NotAuthorizedException.class);
                if (!hasAuthorizationError)
                    throw e;

                handleLogin();
                return function.get();
            }
        }

        private void handleLogin() throws Exception {
            LoginType loginType = sdkConfigurationProvider.getLoginType();
            switch (loginType) {
                case CREDENTIALS:
                    login();
                    break;
                case SAML:
                    samlLogin();
                    break;
                case SSO:
                    ssoLogin();
                    break;
                default:
                    String errorMessage = String.format("HandleAuthorizationFailureCommand does not support the following login type: '%s'", loginType);
                    throw new Exception(errorMessage);
            }
        }
    }
}
