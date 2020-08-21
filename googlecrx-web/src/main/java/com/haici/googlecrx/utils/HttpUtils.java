package com.haici.googlecrx.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpStatus;

import com.haici.googlecrx.utils.callback.ResponseBodyCallback;

/**
 * 使用该类时，注意在实例终止运行时做HttpUtils.httpClientHelper.destroy()操作。webapp服务可以配置# HttpShutdownServletContextListener，完成相应操作。
 * 
 * @author dengzhineng
 * @date 2016年8月26日
 */
public class HttpUtils {

    private static final int TIME_OUT = 15;

    public static final HttpClientHelper httpClientHelper;

    static {
        httpClientHelper = new HttpClientHelper();
        httpClientHelper.setConnectionTimeout(2000);
        httpClientHelper.setSoTimeout(TIME_OUT * 1000);
    }

    /**
     * 通过HTTP GET 发送参数
     * 
     * @param httpUrl
     * @param parameter
     * @throws IOException
     */
    public static String sendGet(String httpUrl, Map<String, String> parameter) throws IOException {
        ResponseBodyCallback callback = new ResponseBodyCallback(HttpHelper.DEFAULT_CHARSET);

        int status = httpClientHelper.get(httpUrl, parameter, HttpHelper.DEFAULT_CHARSET, (Cookie[]) null, (Header[]) null, callback);
        if (status != HttpStatus.SC_OK) {
            throw new IOException("when GET url [" + httpUrl + "] with para=" + parameter + ", httpstatus=" + status);
        }

        return callback.getBody();
    }

    /**
     * 通过HTTP GET 发送参数
     * 
     * @param httpUrl
     * @throws IOException
     */
    public static String sendGet(String httpUrl) throws IOException {
        return sendGet(httpUrl, null);
    }

    /**
     * 使用HTTP POST 发送文本
     * 
     * @param httpUrl 发送的地址
     * @param postBody 发送的内容
     * @return 返回HTTP SERVER的处理结果,如果返回null,发送失败
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static String sentPost(String httpUrl, String postBody) throws Exception {
        return sentPost(httpUrl, postBody, HttpHelper.DEFAULT_CHARSET, null);
    }

    /**
     * 使用HTTP POST 发送文本
     * 
     * @param httpUrl 发送的地址
     * @param postBody 发送的内容
     * @return 返回HTTP SERVER的处理结果,如果返回null,发送失败
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static String sentPost(String httpUrl, String postBody, String encoding) throws Exception {
        return sentPost(httpUrl, postBody, encoding, null);
    }

    /**
     * 使用HTTP POST 发送文本
     * 
     * @param httpUrl 目的地址
     * @param postBody post的包体
     * @param headerMap 增加的Http头信息
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static String sentPost(String httpUrl, String postBody, Map<String, String> headerMap) throws Exception {
        return sentPost(httpUrl, postBody, HttpHelper.DEFAULT_CHARSET, headerMap);
    }

    /**
     * 使用HTTP POST 发送文本
     * 
     * @param httpUrl 发送的地址
     * @param postBody 发送的内容
     * @param encoding 发送的内容的编码
     * @param headerMap 增加的Http头信息
     * @return 返回HTTP SERVER的处理结果,如果返回null,发送失败
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static String sentPost(String httpUrl, String postBody, String encoding, Map<String, String> headerMap) throws Exception {
        ResponseBodyCallback callback = new ResponseBodyCallback(encoding);

        int status = httpClientHelper.post(httpUrl, postBody == null ? new ByteArrayInputStream(new byte[0]) : new ByteArrayInputStream(postBody.getBytes(encoding)), postBody, (Cookie[]) null, (Header[]) null, callback);
        if (status != HttpStatus.SC_OK) {
            throw new IOException("when POST url [" + httpUrl + "] with postBody=" + postBody + ", httpstatus=" + status);
        }

        return callback.getBody();
    }
}
