package com.cx.sdk.IntegrationTests;

import java.net.URL;

/**
 * Created by ehuds on 2/25/2017.
 */
public interface SDKConfigurationProvider {
    String getOriginName();
    void setOriginName(String originName);
    URL getCxServerUrl();
}
