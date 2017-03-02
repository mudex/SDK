package com.cx.sdk.api.dtos;

import java.util.Map;

/**
 * Created by ehuds on 2/23/2017.
 */
public class SessionDTO {
    private String sessionId;
    private Map<String, String> cookies;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }
}
