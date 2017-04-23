package com.cx.sdk.api;

import com.cx.sdk.api.dtos.EngineConfigurationDTO;
import com.cx.sdk.api.dtos.PresetDTO;
import com.cx.sdk.api.dtos.SessionDTO;
import com.cx.sdk.api.dtos.TeamDTO;
import com.cx.sdk.domain.exceptions.SdkException;

import java.util.List;

/**
 * Created by ehuds on 2/22/2017.
 */
public interface CxClient {
    SessionDTO login() throws SdkException;

    List<EngineConfigurationDTO> getEngineConfigurations() throws CxClientException;
    List<PresetDTO> getPresets() throws CxClientException;
    List<TeamDTO> getTeams() throws CxClientException;

//    void validateProjectName(String projectName, String teamId) throws CxClientException;
}
