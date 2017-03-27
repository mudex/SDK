package com.cx.sdk.domain;

import java.util.Map;

/**
 * Created by ehuds on 2/23/2017.
 */
public class Session {
    private String sessionId;
    private Map<String, String> cookies;
    private boolean isScanner;
    private boolean isAllowedToChangeNotExploitable;
    private boolean isIsAllowedToModifyResultDetails;

    public Session(String sessionId, Map<String, String> cookies,
                   boolean isScanner, boolean isAllowedToChangeNotExploitable,
                   boolean isIsAllowedToModifyResultDetails) {
        this.sessionId = sessionId;
        this.cookies = cookies;
        this.isScanner = isScanner;
        this.isAllowedToChangeNotExploitable = isAllowedToChangeNotExploitable;
        this.isIsAllowedToModifyResultDetails = isIsAllowedToModifyResultDetails;
    }
    public String getSessionId() {
        return sessionId;
    }
    public Map<String, String> getCookies() { return cookies; }
    public boolean getIsScanner() { return isScanner; }
    public boolean getIsAllowedToChangeNotExploitable() { return isAllowedToChangeNotExploitable; }
}
