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
    private final ProjectProvider projectProvider;

    private static Session singletonSession;

    @Inject
    private CxClientImpl(LoginService loginService,
                         SDKConfigurationProvider sdkConfigurationProvider,
                         ConfigurationProvider configurationProvider,
                         PresetProvider presetProvider,
                         TeamProvider teamProvider,
                         ProjectProvider projectProvider) {
        this.loginService = loginService;
        this.sdkConfigurationProvider = sdkConfigurationProvider;
        this.configurationProvider = configurationProvider;
        this.presetProvider = presetProvider;
        this.teamProvider = teamProvider;
        this.projectProvider = projectProvider;
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
    public SessionDTO login() throws SdkException {
        LoginType loginType = sdkConfigurationProvider.getLoginType();

        switch (loginType) {
            case CREDENTIALS:
                singletonSession = loginService.login();
                break;
            case SAML:
                singletonSession = loginService.samlLogin();
                break;
            case SSO:
                singletonSession = loginService.ssoLogin();
                break;
            default:
                String errorMessage = String.format("login does not support the following login type: '%s'", loginType);
                throw new SdkException(errorMessage);
        }

        return modelMapper.map(singletonSession, SessionDTO.class);
    }

    @Override
    public List<EngineConfigurationDTO> getEngineConfigurations() throws CxClientException {
        AuthorizedActionInvoker<List<EngineConfiguration>> action = new AuthorizedActionInvoker<>();
        try {
            List<EngineConfiguration> engineConfigurations = action.invoke(() -> configurationProvider.getEngineConfigurations(singletonSession));
            List<EngineConfigurationDTO> dtos = engineConfigurations.stream()
                    .map(engineConfiguration -> modelMapper.map(engineConfiguration, EngineConfigurationDTO.class))
                    .collect(Collectors.toList());
            return dtos;
        } catch (SdkException sdk) {
            throw new CxClientException(sdk.getMessage());
        }
    }

    @Override
    public List<PresetDTO> getPresets() throws CxClientException {
        AuthorizedActionInvoker<List<Preset>> action = new AuthorizedActionInvoker<>();
        try {
            List<Preset> presets = action.invoke(() -> presetProvider.getPresets(singletonSession));
            List<PresetDTO> dtos = presets.stream()
                    .map(preset -> modelMapper.map(preset, PresetDTO.class))
                    .collect(Collectors.toList());
            return dtos;
        } catch (SdkException sdk) {
            throw new CxClientException(sdk.getMessage());
        }
    }


    @Override
    public List<TeamDTO> getTeams() throws CxClientException {
        AuthorizedActionInvoker<List<Team>> action = new AuthorizedActionInvoker<>();
        try {
            List<Team> teams = action.invoke(() -> teamProvider.getTeams(singletonSession));
            List<TeamDTO> dtos = teams.stream()
                    .map(team -> modelMapper.map(team, TeamDTO.class))
                    .collect(Collectors.toList());
            return dtos;
        }
        catch(SdkException sdk) {
            throw new CxClientException(sdk.getMessage());
        }
    }

    @Override
    public void validateProjectName(String projectName, String teamId) throws CxClientException {
        AuthorizedActionInvoker<Boolean> action = new AuthorizedActionInvoker<>();
        try {
            Boolean isValid = action.invoke(() -> projectProvider.isValidProjectName(singletonSession, projectName, teamId));
            if(!isValid){
                throw new CxClientException("Invalid Project Name");
            }
        }
        catch(SdkException sdk) {
            throw new CxClientException(sdk.getMessage());
        }
    }

    public class AuthorizedActionInvoker<T> {
        public T invoke(Supplier<T> function) throws SdkException {
            try {
                if (singletonSession == null) {
                    login();
                }
                return function.get();
            }
            catch(NotAuthorizedException sessionExpiredException) {
                return loginAndHandleFunction(function);
            }
        }

        private T loginAndHandleFunction(Supplier<T> function) throws SdkException
        {
            login();
            return function.get();
        }
    }
}
