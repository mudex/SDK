package com.cx.sdk.application.contracts.providers;

import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;

/**
 * Created by victork on 23/04/2017.
 */
public interface ProjectProvider {
    Boolean isValidProjectName(Session session, String projectName, String teamId) throws SdkException;
}
