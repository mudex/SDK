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

    boolean isTokenExpired(Long expirationTime);

    SessionDTO getAccessTokenFromRefreshToken(String refreshToken);

    boolean isCxWebServiceAvailable();
}
