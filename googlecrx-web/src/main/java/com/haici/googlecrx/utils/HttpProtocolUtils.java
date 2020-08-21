package com.haici.googlecrx.utils;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;

/**
 * 
 * @author dengzhineng
 * @date 2016年8月26日
 */
public class HttpProtocolUtils {
    public static Protocol registerProtocol(String id, Protocol p) {
        Protocol.registerProtocol(id, p);
        return p;
    }

    public static Protocol registerProtocol(String id, SSLProtocolSocketFactory factory, int defPort) {
        Protocol p = new Protocol("https", (ProtocolSocketFactory) factory, defPort);
        Protocol.registerProtocol(id, p);
        return p;
    }

}