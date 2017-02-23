package com.cx.sdk.api;

import com.cx.sdk.DTOs.Authentication;

/**
 * Created by ehuds on 2/22/2017.
 */
public interface CxClient {
    Authentication login(String userName, String password);
}
