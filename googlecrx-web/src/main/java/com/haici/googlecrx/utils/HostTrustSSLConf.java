package com.haici.googlecrx.utils;

import java.net.URL;
import java.util.Collection;

public class HostTrustSSLConf {
    private Collection<String> trustHosts;
    private URL truststoreUrl;
    private String truststorePassword;

    public Collection<String> getTrustHosts() {
        return trustHosts;
    }

    public URL getTruststoreUrl() {
        return truststoreUrl;
    }

    public String getTruststorePassword() {
        return truststorePassword;
    }

    public void setTrustHosts(Collection<String> trustHosts) {
        this.trustHosts = trustHosts;
    }

    public void setTruststoreUrl(URL truststoreUrl) {
        this.truststoreUrl = truststoreUrl;
    }

    public void setTruststorePassword(String truststorePassword) {
        this.truststorePassword = truststorePassword;
    }
}
