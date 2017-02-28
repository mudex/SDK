package com.cx.sdk;

import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ehuds on 2/28/2017.
 */
public class RestResourcesURIBuilder {
    final static String applicationName = "CxRestApi";
    URL BuildLoginURL(URL serverUrl) throws MalformedURLException {
        return new URL(serverUrl, applicationName + "/auth/login");
    }
}
