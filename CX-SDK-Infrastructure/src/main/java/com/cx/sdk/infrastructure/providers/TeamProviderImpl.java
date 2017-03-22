package com.cx.sdk.infrastructure.providers;

import com.checkmarx.v7.CxWSBasicRepsonse;
import com.checkmarx.v7.CxWSResponseGroupList;
import com.checkmarx.v7.CxWSResponsePresetList;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.application.contracts.providers.TeamProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.Preset;
import com.cx.sdk.domain.entities.Team;
import com.cx.sdk.infrastructure.CxSoapClient;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victork on 22/03/2017.
 */
public class TeamProviderImpl implements TeamProvider {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CxSoapClient cxSoapClient;
    private final SDKConfigurationProvider sdkConfigurationProvider;

    @Inject
    public TeamProviderImpl(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
        this.cxSoapClient = new CxSoapClient(this.sdkConfigurationProvider);
    }

    @Override
    public List<Team> getTeams(Session session) throws Exception {
        List<Team> teams = new ArrayList<>();
        try {
            CxWSResponseGroupList response = this.cxSoapClient.getTeams(session);
            for(com.checkmarx.v7.Group group : response.getGroupList().getGroup()) {
                teams.add(new Team(group.getID(), group.getGroupName()));
            }
        }
        catch (Exception e) {
            logger.error("[SDK][TeamProviderImpl] Failed to get teams", e);
            throw e;
        }
        return teams;
    }
}
