package com.cx.sdk.api;

import com.cx.sdk.api.dtos.EngineConfigurationDTO;
import com.cx.sdk.api.dtos.PresetDTO;
import com.cx.sdk.api.dtos.SessionDTO;
import com.cx.sdk.api.dtos.TeamDTO;

import java.util.List;

/**
 * Created by ehuds on 2/22/2017.
 */
public interface CxClient {
    SessionDTO login() throws Exception;

    List<EngineConfigurationDTO> getEngineConfigurations() throws Exception;
    List<PresetDTO> getPresets() throws Exception;
    List<TeamDTO> getTeams() throws Exception;
}
