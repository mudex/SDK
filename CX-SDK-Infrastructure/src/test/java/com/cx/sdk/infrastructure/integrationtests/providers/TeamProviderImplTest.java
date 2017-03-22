package com.cx.sdk.infrastructure.integrationtests.providers;

import com.cx.sdk.application.contracts.providers.TeamProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.Team;
import com.cx.sdk.infrastructure.providers.TeamProviderImpl;
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

    @Test
    public void getTeams() throws Exception {
        // Arrange
        TeamProvider provider = createProvider();
        Session session = createSession();

        // Act
        List<Team> result = provider.getTeams(session);

        // Assert
        assertTrue(result.size() > 0);
    }

}