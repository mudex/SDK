package com.cx.sdk.infrastructure.integrationtests.providers;

import com.cx.sdk.application.contracts.exceptions.NotAuthorizedException;
import com.cx.sdk.application.contracts.providers.ConfigurationProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.Configuration;
import com.cx.sdk.infrastructure.providers.ConfigurationProviderImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by victork on 22/03/2017.
 */
public class ConfigurationProviderImplTest extends ProviderTestBase {
    private Session session;

    private ConfigurationProvider createProvider() {
        return new ConfigurationProviderImpl(sdkConfigurationProvider);
    }

    @Before
    public void setUp() throws Exception {
        session = createValidSession();
    }

    @Test
    public void getEngineConfigurations_shouldSucceed_givenValidSession() throws Exception {
        // Arrange
        ConfigurationProvider provider = createProvider();

        // Act
        List<Configuration> result = provider.getEngineConfigurations(session);

        // Assert
        assertTrue(result.size() > 0);
    }

    @Test(expected = NotAuthorizedException.class)
    public void getEngineConfigurations_shouldFail_givenInvalidSession() throws Exception {
        // Arrange
        ConfigurationProvider provider = createProvider();
        Session invalidSession = createInvalidSession();

        // Act & Assert
        provider.getEngineConfigurations(invalidSession);
    }
}