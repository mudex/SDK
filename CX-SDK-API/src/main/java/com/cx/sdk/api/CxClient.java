package com.cx.sdk.api;

import com.cx.sdk.api.dtos.EngineConfigurationDTO;
import com.cx.sdk.api.dtos.PresetDTO;
import com.cx.sdk.api.dtos.SessionDTO;
import com.cx.sdk.api.dtos.TeamDTO;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.exceptions.SdkException;

import java.util.List;

/**
 * Created by ehuds on 2/22/2017.
 */
public interface CxClient {
	
    /*
     * @return SessionDTO
     * @throws SdkException
     */
    SessionDTO login() throws SdkException;

    /*
     * @return List<EngineConfigurationDTO>
     * @throws CxClientException
     */
    List<EngineConfigurationDTO> getEngineConfigurations(SessionDTO session) throws CxClientException;
    
    /*
     * @return List<PresetDTO>
     * @throws CxClientException
     */
    List<PresetDTO> getPresets(SessionDTO session) throws CxClientException;
    
    /*
     * @return List<TeamDTO>
     * @throws CxClientException
     */
    List<TeamDTO> getTeams(SessionDTO session) throws CxClientException;
    
    /*
     * @param projectName
     * @param teamId
     * @return Boolean
     * @throws CxClientException
     */
    Boolean validateProjectName(String projectName, String teamId, SessionDTO session) throws CxClientException;

    boolean isTokenExpired(Long expirationTime);

    SessionDTO getAccessTokenFromRefreshToken(String refreshToken);
}
