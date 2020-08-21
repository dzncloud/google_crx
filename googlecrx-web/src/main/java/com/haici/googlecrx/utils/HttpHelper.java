package com.haici.googlecrx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;

import com.haici.googlecrx.utils.callback.HttpCallback;

/**
 * 
 * @author dengzhineng
 * @date 2016年8月26日
 */
public interface HttpHelper {

    public static final String HEADER_NAME_CONTENT_TYPE = "Content-Type";

    public static final String DEFAULT_CHARSET = "UTF-8";

    public <T> int get(String address, Map<String, T> data) throws IOException;

    public <T> int get(String address, Map<String, T> data, HttpCallback callback) throws IOException;

    public <T> int get(String address, Map<String, T> data, String charset, Cookie[] cookies, Header[] headers, HttpCallback callback) throws IOException;

    public <T> int get(String address, Map<String, T> data, String charset, Map<String, String> cookies, Map<String, String> headers, HttpCallback callback) throws IOException;

    public int get(String address, String queryStr, String charset, Map<String, String> cookies, Header[] headers, HttpCallback callback) throws IOException;

    public <T> int post(String address, Map<String, T> data) throws IOException;

    public <T> int post(String address, Map<String, T> data, HttpCallback callback) throws IOException;

    public <T> int post(String address, Map<String, T> data, String charset, Map<String, String> cookies, Map<String, String> headers, HttpCallback callback) throws IOException;

    public <T> int post(String address, Map<String, T> data, String charset, Cookie[] cookies, Header[] headers, HttpCallback callback) throws IOException;

    public int post(String address, InputStream data, String charset, Cookie[] cookies, Header[] headers, HttpCallback callback) throws IOException;

    public int post(String address, InputStream data, String charset, Map<String, String> cookies, Map<String, String> headers, HttpCallback callback) throws IOException;

    public int exec(HttpMethod method, HttpState httpState, HttpCallback callback) throws IOException;

    public int post(String address, byte[] data, String charset, Map<String, String> cookies, Header[] headers, HttpCallback callback) throws IOException;

    public int post(String address, String data, String charset, String contentType, HttpCallback callback) throws IOException;

    public void destroy();

    public <T> int put(String address, Map<String, T> data) throws IOException;

    public <T> int put(String address, Map<String, T> data, HttpCallback callback) throws IOException;

    public <T> int put(String address, Map<String, T> data, String charset, Cookie[] cookies, Header[] headers, HttpCallback callback) throws IOException;

    public int put(String address, InputStream data, String charset, Cookie[] cookies, Header[] headers, HttpCallback callback) throws IOException;

    public int put(String address, byte[] data, String charset, Cookie[] cookies, Header[] headers, HttpCallback callback) throws IOException;

    public int put(String address, String data, String charset, String contentType, HttpCallback callback) throws IOException;

}
