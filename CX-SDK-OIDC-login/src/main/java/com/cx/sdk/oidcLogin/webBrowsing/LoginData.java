package com.cx.sdk.oidcLogin.webBrowsing;

public class LoginData {

    private boolean wasCanceled;
    private String accessToken;
    private String refreshToken;

    private Long accessTokenExpirationInMillis;

    public LoginData(String accessToken, String refreshToken, Long accessTokenExpirationInMillis) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpirationInMillis = accessTokenExpirationInMillis;
    }

    public LoginData(boolean wasCanceled) {
        this.wasCanceled = wasCanceled;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public boolean wasCanceled() {
        return wasCanceled;
    }

    public Long getAccessTokenExpirationInMillis() {
        return accessTokenExpirationInMillis;
    }

}