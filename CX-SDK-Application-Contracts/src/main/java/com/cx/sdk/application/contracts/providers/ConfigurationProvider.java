package com.cx.sdk.application.contracts.providers;

import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.Configuration;

import java.util.List;

/**
 * Created by victork on 22/03/2017.
 */
public interface ConfigurationProvider {
    List<Configuration> getEngineConfigurations(Session session) throws Exception;
}
