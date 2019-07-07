package com.cx.sdk.infrastructure.proxy;

import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.domain.entities.ProxyParams;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ConnectionFactory implements HttpURLConnectionFactory {

    private final SDKConfigurationProvider sdkConfigurationProvider;
    private SSLContext sslContext;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

    @Inject
    public ConnectionFactory(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
    }

    @Override
    public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        Proxy proxy = null;
        Proxy.Type proxyType = null;
        ProxyParams proxyParams = sdkConfigurationProvider.getProxyParams();
        if (proxyParams.getType() != null) {
            logger.debug("Setting proxy for URL connection");
            if (proxyParams.getType().equals("HTTPS")) {
                proxyType = Proxy.Type.HTTP;
                logger.debug("Proxy type is HTTPS");
            } else {
                proxyType = Proxy.Type.valueOf(proxyParams.getType());
                logger.debug("Proxy type is: " + proxyParams.getType());
            }
            proxy = new Proxy(proxyType, new InetSocketAddress(proxyParams.getServer(), proxyParams.getPort()));
        } else {
            proxy = Proxy.NO_PROXY;
        }
        HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
        if (con instanceof HttpsURLConnection) {
            logger.debug("Connection is HttpsURLConnection, setting hostname verifier and SSL socket factory");
            HttpsURLConnection httpsCon = (HttpsURLConnection) url.openConnection(proxy);
            httpsCon.setHostnameVerifier(getHostnameVerifier());
            httpsCon.setSSLSocketFactory(getSslContext().getSocketFactory());
            return httpsCon;
        } else {
            return con;
        }
    }

    public SSLContext getSslContext() {
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new SecureTrustManager()}, new SecureRandom());
        } catch (KeyManagementException ex) {
            logger.error(ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage());
        }
        return sslContext;
    }

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname,
                                  javax.net.ssl.SSLSession sslSession) {
                return true;
            }
        };
    }


}