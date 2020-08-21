package com.haici.googlecrx.utils.callback;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpMethod;

import com.haici.googlecrx.utils.HttpClientHelper;

/**
 * 
 * @author dengzhineng
 * @date 2016年8月26日
 */
public interface HttpCallback {

    /**
     * callback when get response. if you need headers, httpMethod.getResponseHeaders()
     * 
     * @param httpMethod
     * @param cookies
     * @throws IOException
     * @see HttpClientHelper
     */
    public void callback(int httpstatus, HttpMethod httpMethod, Cookie[] cookies) throws IOException;
}
