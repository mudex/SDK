package com.cx.sdk.infrastructure.integrationtests.providers;

import com.cx.sdk.application.contracts.exceptions.NotAuthorizedException;
import com.cx.sdk.application.contracts.providers.PresetProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.Preset;
import com.cx.sdk.infrastructure.providers.PresetProviderImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by victork on 22/03/2017.
 */
public class PresetProviderImplTest extends ProviderTestBase {
    private Session session;

    private PresetProvider createProvider() {
        return new PresetProviderImpl(sdkConfigurationProvider);
    }

    @Before
    public void setUp() throws Exception {
        session = createValidSession();
    }

    @Test
    public void getPresets_shouldSucceed_givenValidSession() throws Exception {
        // Arrange
        PresetProvider provider = createProvider();

        // Act
        List<Preset> result = provider.getPresets(session);

        // Assert
        assertTrue(result.size() > 0);
    }

    @Test(expected = NotAuthorizedException.class)
    public void getPresets_shouldThrow_givenInvalidSession() throws Exception {
        // Arrange
        PresetProvider provider = createProvider();
        Session invalidSession = createInvalidSession();

        // Act & Assert
        provider.getPresets(invalidSession);
    }
}