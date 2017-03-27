package com.cx.sdk.api.unittests;

import com.cx.sdk.api.CxClient;
import com.cx.sdk.api.CxClientImpl;
import com.cx.sdk.api.SdkConfiguration;
import com.cx.sdk.api.dtos.*;
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
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by victork on 02/03/2017.
 */
public class CxClientImplTest {
    private final String USERNAME = "user";
    private final String PASSWORD = "pass";

    private Session session;

    private final LoginService loginService = mock(LoginService.class);
    private final SDKConfigurationProvider sdkConfigurationProvider = mock(SDKConfigurationProvider.class);
    private final ConfigurationProvider configurationProvider = mock(ConfigurationProvider.class);
    private final PresetProvider presetProvider = mock(PresetProvider.class);
    private final TeamProvider teamProvider = mock(TeamProvider.class);

    private CxClient createClient() throws Exception {
        Constructor<?>[] constructors = CxClientImpl.class.getDeclaredConstructors();
        constructors[0].setAccessible(true);
        CxClient clientImp = (CxClientImpl)constructors[0].newInstance(
                loginService,
                sdkConfigurationProvider,
                configurationProvider,
                presetProvider,
                teamProvider);
        return clientImp;
    }

    @Before
    public void setup() {
        Map<String, String> cookies = new HashMap<>();
        cookies.put("key", "value");
        session = new Session("sessionId", cookies, true, true, true);
    }

    @Test
    public void createNewInstance_shouldSucceed_givenProvidedAllRequiredValues() {
        // Arrange
        SdkConfiguration configuration = mock(SdkConfiguration.class);
        when(configuration.getCxServerUrl()).thenReturn(getFakeUrl());
        when(configuration.getLoginType()).thenReturn(LoginTypeDTO.CREDENTIALS);

        // Act
        CxClient client = CxClientImpl.createNewInstance(configuration);

        // Assert
        assertNotNull(client);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewInstance_shouldThrow_givenMissingCxServerUrl() {
        // Arrange
        SdkConfiguration configuration = mock(SdkConfiguration.class);
        when(configuration.getLoginType()).thenReturn(LoginTypeDTO.CREDENTIALS);

        // Act & Assert
        CxClientImpl.createNewInstance(configuration);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewInstance_shouldThrow_givenMissingLoginType() {
        // Arrange
        SdkConfiguration configuration = mock(SdkConfiguration.class);
        when(configuration.getCxServerUrl()).thenReturn(getFakeUrl());

        // Act & Assert
        CxClientImpl.createNewInstance(configuration);
    }

    @Test
    public void login_shouldReturnSessionDto_givenSuccess() throws Exception  {
        // Arrange
        CxClient client = createClient();
        when(loginService.login()).thenReturn(session);

        // Act
        SessionDTO result = client.login();

        // Assert
        validateSession(session, result);
    }

    @Test
    public void ssoLogin_shouldReturnSessionDto_givenSuccess() throws Exception  {
        // Arrange
        CxClient client = createClient();
        when(loginService.ssoLogin()).thenReturn(session);

        // Act
        SessionDTO result = client.ssoLogin();

        // Assert
        validateSession(session, result);
    }

    @Test
    public void samlLogin_shouldReturnSessionDto_givenSuccess() throws Exception  {
        // Arrange
        CxClient client = createClient();
        when(loginService.samlLogin()).thenReturn(session);

        // Act
        SessionDTO result = client.samlLogin();

        // Assert
        validateSession(session, result);
    }

    @Test
    public void getEngineConfigurations_shouldSucceed_givenValidSession() throws Exception {
        // Arrange
        CxClient client = createClient();
        setupIsLoggedInWithCredentials(client);
        List<EngineConfiguration> configs = new ArrayList<>();
        EngineConfiguration configuration = new EngineConfiguration("id", "name");
        configs.add(configuration);
        when(configurationProvider.getEngineConfigurations(session)).thenReturn(configs);

        // Act
        List<EngineConfigurationDTO> result = client.getEngineConfigurations();

        // Assert
        assertTrue(result.size() == 1);
        assertEquals(configs.get(0).getId(), configuration.getId());
        assertEquals(configs.get(0).getName(), configuration.getName());
    }

    @Test(expected = SdkException.class)
    public void getEngineConfigurations_shouldFail_givenCannotLogin() throws Exception {
        // Arrange
        CxClient client = createClient();
        setupHasNoValidCredentials();

        // Act & Assert
        client.getEngineConfigurations();
    }

    @Test
    public void getPresets_shouldSucceed_givenValidSession() throws Exception {
        // Arrange
        CxClient client = createClient();
        setupIsLoggedInWithCredentials(client);
        List<Preset> presets = new ArrayList<>();
        Preset preset = new Preset("id", "name");
        presets.add(preset);
        when(presetProvider.getPresets(session)).thenReturn(presets);

        // Act
        List<PresetDTO> result = client.getPresets();

        // Assert
        assertTrue(result.size() == 1);
        assertEquals(presets.get(0).getId(), preset.getId());
        assertEquals(presets.get(0).getName(), preset.getName());
    }

    @Test(expected = SdkException.class)
    public void getPresets_shouldFail_givenCannotLogin() throws Exception {
        // Arrange
        CxClient client = createClient();
        setupHasNoValidCredentials();

        // Act & Assert
        client.getPresets();
    }

    @Test
    public void getTeams_shouldSucceed_givenValidSession() throws Exception {
        // Arrange
        CxClient client = createClient();
        setupIsLoggedInWithCredentials(client);
        List<Team> teams = new ArrayList<>();
        Team team = new Team("id", "name");
        teams.add(team);
        when(teamProvider.getTeams(session)).thenReturn(teams);

        // Act
        List<TeamDTO> result = client.getTeams();

        // Assert
        assertTrue(result.size() == 1);
        assertEquals(teams.get(0).getId(), team.getId());
        assertEquals(teams.get(0).getName(), team.getName());
    }

    @Test(expected = SdkException.class)
    public void getTeams_shouldFail_givenCannotLogin() throws Exception {
        // Arrange
        CxClient client = createClient();
        setupHasNoValidCredentials();

        // Act & Assert
        client.getTeams();
    }

    @Test
    public void handleAuthorizationFailureCommand_shouldLogin_givenHasNoSession() throws Exception {
        // Arrange
        CxClientImpl container = (CxClientImpl)createClient();
        CxClientImpl.HandleAuthorizationFailureCommand<String> command = container.new HandleAuthorizationFailureCommand<>();
        DummyInterface dummyInterface = mock(DummyInterface.class);
        String expectedResult = "my-result";
        when(dummyInterface.foo()).thenReturn(expectedResult);
        when(loginService.login()).thenReturn(session);
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.CREDENTIALS);

        // Act
        String result = command.run(dummyInterface::foo);

        // Assert
        assertEquals(result, expectedResult);
        verify(loginService).login();
    }

    @Test
    public void handleAuthorizationFailureCommand_shouldLoginWithCredentials_givenFailedDueToNotAuthenticated() throws Exception {
        // Arrange
        CxClientImpl container = (CxClientImpl)createClient();
        CxClientImpl.HandleAuthorizationFailureCommand<String> command = container.new HandleAuthorizationFailureCommand<>();
        DummyInterface dummyInterface = mock(DummyInterface.class);
        String expectedResult = "my-result";
        when(dummyInterface.foo())
                .thenThrow(new NotAuthorizedException("OMG!"))
                .thenReturn(expectedResult);
        when(loginService.login()).thenReturn(session);
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.CREDENTIALS);

        // Act
        String result = command.run(dummyInterface::foo);

        // Assert
        assertEquals(result, expectedResult);
        verify(loginService, times(2)).login();
    }

    @Test
    public void handleAuthorizationFailureCommand_shouldLoginWithSSO_givenFailedDueToNotAuthenticated() throws Exception {
        // Arrange
        CxClientImpl container = (CxClientImpl)createClient();
        CxClientImpl.HandleAuthorizationFailureCommand<String> command = container.new HandleAuthorizationFailureCommand<>();
        DummyInterface dummyInterface = mock(DummyInterface.class);
        String expectedResult = "my-result";
        when(dummyInterface.foo())
                .thenThrow(new NotAuthorizedException("OMG!"))
                .thenReturn(expectedResult);
        when(loginService.ssoLogin()).thenReturn(session);
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.SSO);

        // Act
        String result = command.run(dummyInterface::foo);

        // Assert
        assertEquals(result, expectedResult);
        verify(loginService, times(2)).ssoLogin();
    }

    @Test
    public void handleAuthorizationFailureCommand_shouldLoginWithSAML_givenFailedDueToNotAuthenticated() throws Exception {
        // Arrange
        CxClientImpl container = (CxClientImpl)createClient();
        CxClientImpl.HandleAuthorizationFailureCommand<String> command = container.new HandleAuthorizationFailureCommand<>();
        DummyInterface dummyInterface = mock(DummyInterface.class);
        String expectedResult = "my-result";
        when(dummyInterface.foo())
                .thenThrow(new NotAuthorizedException("OMG!"))
                .thenReturn(expectedResult);
        when(loginService.samlLogin()).thenReturn(session);
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.SAML);

        // Act
        String result = command.run(dummyInterface::foo);

        // Assert
        assertEquals(result, expectedResult);
        verify(loginService, times(2)).samlLogin();
    }

    @Test(expected = RuntimeException.class)
    public void handleAuthorizationFailureCommand_shouldThrow_givenUnhandledError() throws Exception {
        // Arrange
        CxClientImpl container = (CxClientImpl)createClient();
        CxClientImpl.HandleAuthorizationFailureCommand<String> command = container.new HandleAuthorizationFailureCommand<>();
        DummyInterface dummyInterface = mock(DummyInterface.class);
        when(dummyInterface.foo()).thenThrow(new RuntimeException());

        // Act & Assert
        command.run(dummyInterface::foo);
    }

    private URL getFakeUrl() {
        try {
            return new URL("http://some-fake-url.com");
        }
        catch(MalformedURLException e) {
            return null;
        }
    }

    private void validateSession(Session session, SessionDTO sessionDTO) {
        assertEquals(session.getSessionId(), sessionDTO.getSessionId());
        assertEquals(session.getCookies(), sessionDTO.getCookies());
        assertEquals(session.getIsScanner(), sessionDTO.getIsScanner());
        assertEquals(session.getIsAllowedToChangeNotExploitable(), sessionDTO.isAllowedToChangeNotExploitable());
    }

    private void setupIsLoggedInWithCredentials(CxClient cxClient) throws Exception {
        when(loginService.login()).thenReturn(session);
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.CREDENTIALS);
        cxClient.login();
    }

    private void setupHasNoValidCredentials() {
        when(loginService.login()).thenThrow(new SdkException("Login failed"));
        when(sdkConfigurationProvider.getLoginType()).thenReturn(LoginType.CREDENTIALS);
    }

    private interface DummyInterface {
        String foo() throws RuntimeException;
    }
}