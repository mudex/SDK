package com.cx.sdk.api.unittests;

import com.cx.sdk.api.CxClient;
import com.cx.sdk.api.CxClientImpl;
import com.cx.sdk.api.SdkConfiguration;
import com.cx.sdk.api.dtos.SessionDTO;
import com.cx.sdk.application.services.LoginService;
import com.cx.sdk.domain.Session;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    public void createNewInstance() {
        // Arrange
        SdkConfiguration configuration = mock(SdkConfiguration.class);

        // Act
        CxClient client = CxClientImpl.createNewInstance(configuration);

        // Assert
        assertNotNull(client);
    }

    @Test
    public void login() throws Exception  {
        // Arrange
        CxClient client = createClient();
        Map<String, String> cookies = new HashMap<>();
        cookies.put("key", "value");
        Session session = new Session("sessionId", cookies);
        when(loginService.login(USERNAME, PASSWORD)).thenReturn(session);

        // Act
        SessionDTO result = client.login(USERNAME, PASSWORD);

        // Assert
        assertEquals(session.getSessionId(), result.getSessionId());
        assertEquals(session.getCookies(), result.getCookies());
    }

}