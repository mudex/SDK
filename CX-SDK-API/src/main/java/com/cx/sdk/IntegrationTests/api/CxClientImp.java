package com.cx.sdk.IntegrationTests.api;

import com.cx.sdk.IntegrationTests.DTOs.SessionDTO;

/**
 * Created by ehuds on 2/22/2017.
 */
public class CxClientImp implements CxClient {
    public SessionDTO login(String userName, String password) {

        return new SessionDTO();
    }
}
