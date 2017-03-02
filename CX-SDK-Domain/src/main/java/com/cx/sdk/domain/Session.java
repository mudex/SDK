package com.cx.sdk.domain;

import java.util.Map;

/**
 * Created by ehuds on 2/23/2017.
 */
public class Session {
    private String sessionId;
    private Map<String, String> cookies;

    public Session(String sessionId, Map<String, String> cookies) {
        this.sessionId = sessionId;
        this.cookies = cookies;
    }
    public String getSessionId() {
        return sessionId;
    }
    public Map<String, String> getCookies() { return cookies; }
}
