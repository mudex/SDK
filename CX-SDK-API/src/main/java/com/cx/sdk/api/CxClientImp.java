package com.cx.sdk.api;

import com.cx.sdk.DTOs.Authentication;

/**
 * Created by ehuds on 2/22/2017.
 */
public class CxClientImp implements CxClient {
    public Authentication login(String userName, String password) {

        return new Authentication();
    }
}
