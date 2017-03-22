package com.cx.sdk.application.contracts.providers;

import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.ProjectSettings;
import com.cx.sdk.domain.exceptions.SdkException;

/**
 * Created by victork on 22/03/2017.
 */
public interface ConfigurationProvider {
    ProjectSettings getProjectConfiguration(Session session, long projectId) throws Exception;
}
