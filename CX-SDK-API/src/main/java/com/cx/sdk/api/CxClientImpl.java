package com.cx.sdk.api;

import com.checkmarx.plugin.common.api.CxOIDCLoginClient;
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

    @Override
    public List<EngineConfigurationDTO> getEngineConfigurations(SessionDTO sessionDTO) throws CxClientException {
        Session session = modelMapper.map(sessionDTO, Session.class);
        return getEngineConfigurationDTOs(session);
    }

    private List<EngineConfigurationDTO> getEngineConfigurationDTOs(Session session) throws CxClientException {
        try {
            List<EngineConfiguration> engineConfigurations = configurationProvider.getEngineConfigurations(session);
            List<EngineConfigurationDTO> dtos = new ArrayList(engineConfigurations.size());

            for (EngineConfiguration configuration: engineConfigurations ) {
                dtos.add(new EngineConfigurationDTO(configuration.getId(), configuration.getName()));
            }
            return dtos;
        } catch (SdkException sdk) {
            throw new CxClientException(sdk.getMessage());
        }
    }

    @Override
    public List<PresetDTO> getPresets(SessionDTO sessionDTO) throws CxClientException {
        Session session = modelMapper.map(sessionDTO, Session.class);
        return getPresetDTOs(session);

    }

    private List<PresetDTO> getPresetDTOs(Session session) throws CxClientException {
        try {
            List<Preset> presets = presetProvider.getPresets(session);
            List<PresetDTO> dtos = new ArrayList(presets.size());

            for (Preset preset: presets ) {
                dtos.add(new PresetDTO(preset.getId(), preset.getName()));
            }

            return dtos;
        } catch (SdkException sdk) {
            throw new CxClientException(sdk.getMessage());
        }
    }


    @Override
    public List<TeamDTO> getTeams(SessionDTO sessionDTO) throws CxClientException {
        Session session = modelMapper.map(sessionDTO, Session.class);
        return getTeamDTOs(session);
    }

    private List<TeamDTO> getTeamDTOs(Session session) throws CxClientException {
        try {

            List<Team> teams = teamProvider.getTeams(singletonSession);
            List<TeamDTO> dtos = new ArrayList(teams.size());

            for (Team team: teams ) {
                dtos.add(new TeamDTO(team.getId(), team.getName()));
            }

            return dtos;

        }
        catch(SdkException sdk) {
            throw new CxClientException(sdk.getMessage());
        }
    }

    @Override
    public Boolean validateProjectName(String projectName, String teamId, SessionDTO sessionDTO) throws CxClientException {
        Session session = modelMapper.map(sessionDTO, Session.class);
        return isValid(projectName, teamId, session);

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

    private Boolean isValid(String projectName, String teamId, Session session) throws CxClientException {
        try {
            Boolean isValid = projectProvider.isValidProjectName(session, projectName, teamId);
            return isValid;
        }
        catch(SdkException sdk) {
            throw new CxClientException(sdk.getMessage());
        }
    }

}
