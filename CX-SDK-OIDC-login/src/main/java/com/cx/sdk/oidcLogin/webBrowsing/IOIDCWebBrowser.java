package com.cx.sdk.oidcLogin.webBrowsing;

public interface IOIDCWebBrowser {

    AuthenticationData browseAuthenticationData(String serverUrl, String clientName) throws Exception;

    void disposeBrowser();

    void logout(String idToken);
}
