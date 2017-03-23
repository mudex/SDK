package com.cx.sdk.api.unittests;

import com.cx.sdk.api.CxClient;
import com.cx.sdk.api.CxClientImpl;
import com.cx.sdk.api.SdkConfiguration;
import com.cx.sdk.api.dtos.LoginTypeDTO;
import com.cx.sdk.api.dtos.SessionDTO;
import com.cx.sdk.application.services.LoginService;
import com.cx.sdk.domain.Session;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
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


    private static final LoginService loginService = mock(LoginService.class);

    private CxClient createClient() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] constructors = CxClientImpl.class.getDeclaredConstructors();
        constructors[0].setAccessible(true);
        CxClient clientImp = (CxClientImpl)constructors[0].newInstance(loginService);
        return clientImp;
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
    public void login() throws Exception  {
        // Arrange
        CxClient client = createClient();
        Map<String, String> cookies = new HashMap<>();
        cookies.put("key", "value");
        Session session = new Session("sessionId", cookies, true, true);
        when(loginService.login()).thenReturn(session);

        // Act
        SessionDTO result = client.login();

        // Assert
        assertEquals(session.getSessionId(), result.getSessionId());
        assertEquals(session.getCookies(), result.getCookies());
        assertEquals(session.getIsScanner(), result.getIsScanner());
        assertEquals(session.getIsAllowedToChangeNotExploitable(), result.isAllowedToChangeNotExploitable());
    }

    private URL getFakeUrl() {
        try {
            return new URL("http://some-fake-url.com");
        }
        catch(MalformedURLException e) {
            return null;
        }
    }
}