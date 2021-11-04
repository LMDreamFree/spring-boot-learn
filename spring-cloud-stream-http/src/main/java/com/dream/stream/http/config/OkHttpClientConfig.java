package com.dream.stream.http.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author lim
 * @date 2021/11/4 20:54
 * @className OkHttpClientConfig
 */
@Configuration
public class OkHttpClientConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpClientConfig.class);
    private OkHttpClient okHttpClient;
    private int maxConnections = 200;
    public static final int DEFAULT_CONNECTION_TIMEOUT = 2000;
    public static final boolean DEFAULT_FOLLOW_REDIRECTS = true;
    private long timeToLive = 900L;
    public static final TimeUnit DEFAULT_TIME_TO_LIVE_UNIT = TimeUnit.SECONDS;
    private TimeUnit timeToLiveUnit = DEFAULT_TIME_TO_LIVE_UNIT;

    @Bean
    @ConditionalOnMissingBean({ConnectionPool.class})
    public ConnectionPool connectionPool() {
        return new ConnectionPool(maxConnections, timeToLive, timeToLiveUnit);
    }


    @Bean
    public OkHttpClient client(ConnectionPool connectionPool) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            X509TrustManager disabledTrustManager = new DisableValidationTrustManager();
            TrustManager[] trustManagers = new TrustManager[]{disabledTrustManager};
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new SecureRandom());
            SSLSocketFactory disabledSSLSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(disabledSSLSocketFactory, disabledTrustManager);
            builder.hostnameVerifier(new TrustAllHostnames());
        } catch (NoSuchAlgorithmException var6) {
            LOGGER.warn("Error setting SSLSocketFactory in OKHttpClient", var6);
        } catch (KeyManagementException var7) {
            LOGGER.warn("Error setting SSLSocketFactory in OKHttpClient", var7);
        }
        OkHttpClient okHttpClient = builder
                .connectTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .followRedirects(DEFAULT_FOLLOW_REDIRECTS)
                .connectionPool(connectionPool)
                .build();
        return okHttpClient;
    }

    public static class TrustAllHostnames implements HostnameVerifier {
        public TrustAllHostnames() {
        }

        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }

    public static class DisableValidationTrustManager implements X509TrustManager {
        public DisableValidationTrustManager() {
        }

        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    @PreDestroy
    public void destroy() {
        if (this.okHttpClient != null) {
            this.okHttpClient.dispatcher().executorService().shutdown();
            this.okHttpClient.connectionPool().evictAll();
        }

    }
}
