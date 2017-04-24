package com.cx.sdk.infrastructure.providers;

import com.checkmarx.v7.CxWSBasicRepsonse;
import com.checkmarx.v7.CxWSResponsePresetList;
import com.cx.sdk.application.contracts.exceptions.NotAuthorizedException;
import com.cx.sdk.application.contracts.providers.ProjectProvider;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.Preset;
import com.cx.sdk.domain.exceptions.SdkException;
import com.cx.sdk.domain.exceptions.ValidationException;
import com.cx.sdk.infrastructure.CxSoapClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victork on 23/04/2017.
 */
public class ProjectProviderImpl implements ProjectProvider {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CxSoapClient cxSoapClient;
    private final SDKConfigurationProvider sdkConfigurationProvider;

    @Inject
    public ProjectProviderImpl(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
        this.cxSoapClient = new CxSoapClient(this.sdkConfigurationProvider);
    }

    @Override
    public Boolean isValidProjectName(Session session, String projectName, String teamId) throws SdkException {
        try {
            Boolean isValid = this.cxSoapClient.isProjectNameValid(session, projectName, teamId);
            return isValid;
        }
        catch (SdkException domainException) {
            throw domainException;
        } catch (Exception e) {
            logger.error(String.format("Failed to validate Project Name '%s' with Team ID '%s'", projectName, teamId), e);
            throw new SdkException("Failed to validate project name");
        }
    }
}
