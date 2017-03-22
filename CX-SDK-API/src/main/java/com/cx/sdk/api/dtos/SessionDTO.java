package com.cx.sdk.api.dtos;

import java.util.Map;

/**
 * Created by ehuds on 2/23/2017.
 */
public class SessionDTO {
    private String sessionId;
    private Map<String, String> cookies;
    private boolean isScanner;
    private boolean isAllowedToChangeNotExploitable;

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

    public boolean getIsScanner() {
        return isScanner;
    }

    public void setIsScanner(boolean scanner) {
        isScanner = scanner;
    }

    public boolean isAllowedToChangeNotExploitable() {
        return isAllowedToChangeNotExploitable;
    }

    public void setAllowedToChangeNotExploitable(boolean allowedToChangeNotExploitable) {
        isAllowedToChangeNotExploitable = allowedToChangeNotExploitable;
    }
}
