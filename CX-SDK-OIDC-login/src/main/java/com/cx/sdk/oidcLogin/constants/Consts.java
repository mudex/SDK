package com.cx.sdk.oidcLogin.constants;

public class Consts {

    public static final String CLIENT_ID_KEY = "client_id";
    public static final String CLIENT_VALUE = "ide_sast_client";
    public static final String SCOPE_KEY = "scope";
    public static final String CODE_KEY = "code";
    public static final String GRANT_TYPE_KEY = "grant_type";
    public static final String AUTHORIZATION_CODE_GRANT_TYPE = "authorization_code";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String REDIRECT_URI_KEY = "redirect_uri";
    public static final String RESPONSE_TYPE_VALUE = "code";
    public static final String SCOPE_VALUE = "offline_access openid sast_api sast-permissions";
    public static final String SAST_PREFIX = "/cxrestapi/auth";
    public static final String AUTHORIZATION_ENDPOINT = SAST_PREFIX + "/identity/connect/authorize";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String USER_INFO_ENDPOINT = SAST_PREFIX + "/identity/connect/userinfo";
    public static final String SAVE_SAST_SCAN = "save-sast-scan";
    public static final String MANAGE_RESULTS_COMMENT = "manage-result-comment";
    public static final String MANAGE_RESULTS_EXPLOITABILITY = "manage-result-exploitability";
    public static final String RESPONSE_TYPE_KEY = "response_type";
}