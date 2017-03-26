package com.cx.sdk.infrastructure.providers;

import com.checkmarx.v7.CxWSResponseConfigSetList;
import com.cx.sdk.application.contracts.exceptions.NotAuthorizedException;
import com.cx.sdk.application.contracts.providers.ConfigurationProvider;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.EngineConfiguration;
import com.cx.sdk.infrastructure.CxSoapClient;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victork on 22/03/2017.
 */
public class ConfigurationProviderImpl implements ConfigurationProvider {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CxSoapClient cxSoapClient;
    private final SDKConfigurationProvider sdkConfigurationProvider;

    @Inject
    public ConfigurationProviderImpl(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
        this.cxSoapClient = new CxSoapClient(this.sdkConfigurationProvider);
    }

    @Override
    public List<EngineConfiguration> getEngineConfigurations(Session session) throws RuntimeException {
        List<EngineConfiguration> configurations = new ArrayList<>();
        try {
            CxWSResponseConfigSetList response = this.cxSoapClient.getConfigurations(session);
            for (com.checkmarx.v7.ConfigurationSet configurationSet : response.getConfigSetList().getConfigurationSet()) {
                configurations.add(new EngineConfiguration(Long.toString(configurationSet.getID()), configurationSet.getConfigSetName()));
            }
        }
        catch(NotAuthorizedException unauthorizedException) {
            throw unauthorizedException;
        } catch (Exception e) {
            logger.error("[SDK][ConfigurationProviderImpl] Failed to get configurations", e);
            throw new RuntimeException("Failed to get engine configurations");
        }
        return configurations;
    }
}
