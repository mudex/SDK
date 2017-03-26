package com.cx.sdk.application.contracts.providers;

import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.EngineConfiguration;

import java.util.List;

/**
 * Created by victork on 22/03/2017.
 */
public interface ConfigurationProvider {
    List<EngineConfiguration> getEngineConfigurations(Session session) throws RuntimeException;
}
