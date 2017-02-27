package com.cx.sdk.login;

/**
 * Created by ehuds on 2/23/2017.
 */
public class Session {
    String sessionId;
    public Session(String sessionId) {
        this.sessionId = sessionId;
    }
    String getSessionId() {
        return sessionId;
    }
}
