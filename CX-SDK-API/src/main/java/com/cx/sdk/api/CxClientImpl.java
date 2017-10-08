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
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ehuds on 2/22/2017.
 */
public class CxClientImpl implements CxClient {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static final String LOGIN_CONF_PATH = "resources/login.conf";
    private static final Logger logger = LoggerFactory.getLogger(CxClientImpl.class);
    
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

        initKerberosParameters(configuration);

        singletonSession = null;

        logger.info("Guice: instantiating CxClient");
        try{
            Injector injector = Guice.createInjector(new Bootstrapper(configuration));
            return injector.getInstance(CxClient.class);
        }catch(Throwable e){
            logger.info("in catch");

            logger.info(e.getLocalizedMessage());
            logger.info(e.toString());
            logger.info(e.getMessage());
            logger.info(e.getCause().getMessage());
            logger.info(e.getStackTrace().toString());
            logger.error(e.getLocalizedMessage(),e);
            throw new SdkException(e.getStackTrace().toString() + "\n" + e.getLocalizedMessage());
        }
    }

    private static void initKerberosParameters(SdkConfiguration configuration) {
        if (configuration.useKerberosAuthentication()) {
            System.setProperty("java.security.auth.login.config", new File(LOGIN_CONF_PATH).getAbsolutePath());
            System.setProperty("com.sun.security.auth.module.Krb5LoginModule", "true");
        }
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

        try {

            List<EngineConfiguration> engineConfigurations;
            try {
                if (singletonSession == null) {
                    login();
                }
                engineConfigurations = configurationProvider.getEngineConfigurations(singletonSession);
            }
            catch(NotAuthorizedException sessionExpiredException) {
                login();
                engineConfigurations = configurationProvider.getEngineConfigurations(singletonSession);
            }

            List<EngineConfigurationDTO> dtos = new LinkedList<EngineConfigurationDTO>();
            for (EngineConfiguration engineConfiguration:engineConfigurations) {
                dtos.add(modelMapper.map(engineConfiguration, EngineConfigurationDTO.class));
            }

            return dtos;

        } catch (SdkException sdk) {
            throw new CxClientException(sdk.getMessage());
        }
    }

    @Override
    public List<PresetDTO> getPresets() throws CxClientException {

        try {
            List<Preset> presets;
            try {
                if (singletonSession == null) {
                    login();
                }
                presets = presetProvider.getPresets(singletonSession);
            }
            catch(NotAuthorizedException sessionExpiredException) {
                login();
                presets = presetProvider.getPresets(singletonSession);
            }

            List<PresetDTO> dtos = new LinkedList<PresetDTO>();
            for (Preset preset:presets) {
                dtos.add(modelMapper.map(preset, PresetDTO.class));
            }

            return dtos;
        } catch (SdkException sdk) {
            throw new CxClientException(sdk.getMessage());
        }
    }


    @Override
    public List<TeamDTO> getTeams() throws CxClientException {

        try {
            List<Team> teams;
            try {
                if (singletonSession == null) {
                    login();
                }
                teams = teamProvider.getTeams(singletonSession);
            }
            catch(NotAuthorizedException sessionExpiredException) {
                login();
                teams = teamProvider.getTeams(singletonSession);
            }

            List<TeamDTO> dtos = new LinkedList<TeamDTO>();
            for (Team team:teams) {
                dtos.add(modelMapper.map(team, TeamDTO.class));
            }

            return dtos;
        } catch (SdkException sdk) {
            throw new CxClientException(sdk.getMessage());
        }
    }

    @Override
    public Boolean validateProjectName(String projectName, String teamId) throws CxClientException {

        try {
            Boolean isValid;

            try {
                if (singletonSession == null) {
                    login();
                }
                isValid = projectProvider.isValidProjectName(singletonSession, projectName, teamId);
            }
            catch(NotAuthorizedException sessionExpiredException) {
                login();
                isValid = projectProvider.isValidProjectName(singletonSession, projectName, teamId);
            }

            return isValid;            
        }
        catch(SdkException sdk) {
            throw new CxClientException(sdk.getMessage());
        }
    }
}
