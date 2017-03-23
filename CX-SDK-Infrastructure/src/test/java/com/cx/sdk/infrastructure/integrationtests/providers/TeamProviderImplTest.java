package com.cx.sdk.infrastructure.integrationtests.providers;

import com.cx.sdk.application.contracts.providers.PresetProvider;
import com.cx.sdk.application.contracts.providers.TeamProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.Team;
import com.cx.sdk.infrastructure.providers.PresetProviderImpl;
import com.cx.sdk.infrastructure.providers.TeamProviderImpl;
import com.cx.sdk.application.contracts.exceptions.NotAuthorizedException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by victork on 22/03/2017.
 */
public class TeamProviderImplTest extends ProviderTestBase {
    private TeamProvider createProvider() {
        return new TeamProviderImpl(this.sdkConfigurationProvider);
    }

    private Session session;

    @Before
    public void setUp() throws Exception {
        session = createValidSession();
    }


    @Test
    public void getTeams_shouldReturnTeams_givenValidSession() throws Exception {
        // Arrange
        TeamProvider provider = createProvider();

        // Act
        List<Team> result = provider.getTeams(session);

        // Assert
        assertTrue(result.size() > 0);
    }

    @Test(expected=NotAuthorizedException.class)
    public void getTeams_shouldThrow_givenInvalidSession() throws Exception {
        // Arrange
        TeamProvider provider = createProvider();
        Session session = createInvalidSession();

        // Act & Assert
        provider.getTeams(session);
    }

}