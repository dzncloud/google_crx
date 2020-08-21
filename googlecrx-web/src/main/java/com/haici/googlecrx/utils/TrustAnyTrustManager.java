package com.haici.googlecrx.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 
 * @author dengzhineng
 * @date 2016年8月26日
 */
public class TrustAnyTrustManager implements X509TrustManager {
    public static final TrustAnyTrustManager def = new TrustAnyTrustManager();

    public static final TrustAnyTrustManager getInstance() {
        return def;
    }

    protected TrustAnyTrustManager() {

    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[] {};
    }

}