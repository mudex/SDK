package com.cx.sdk.oidcLogin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class UserInfoDTO {

    @JsonProperty("sast-permissions")
    private ArrayList<String> sastPermissions;

    @JsonProperty("sub")
    private String sub;

    public ArrayList<String> getSastPermissions() {
        return sastPermissions;
    }
}