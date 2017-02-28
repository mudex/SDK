package com.cx.sdk.domain;

import java.util.Map;

/**
 * Created by ehuds on 2/23/2017.
 */
public class Session {
    String sessionId;
    Map<String, String> coockies;
    public Session(String sessionId, Map<String, String> coockies) {
        this.sessionId = sessionId;
        this.coockies = coockies;
    }
    String getSessionId() {
        return sessionId;
    }
}
