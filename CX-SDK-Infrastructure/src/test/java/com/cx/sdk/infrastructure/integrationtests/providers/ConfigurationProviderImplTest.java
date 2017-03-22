package com.cx.sdk.infrastructure.integrationtests.providers;

import com.cx.sdk.application.contracts.providers.ConfigurationProvider;
import com.cx.sdk.application.contracts.providers.PresetProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.Configuration;
import com.cx.sdk.domain.entities.Preset;
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
        session = createSession();
    }

    @Test
    public void getConfigurations() throws Exception {
        // Arrange
        ConfigurationProvider provider = createProvider();

        // Act
        List<Configuration> result = provider.getConfigurations(session);

        // Assert
        assertTrue(result.size() > 0);
    }
}