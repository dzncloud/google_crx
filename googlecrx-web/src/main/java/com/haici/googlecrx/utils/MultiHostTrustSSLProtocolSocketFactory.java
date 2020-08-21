/*
 * $HeadURL$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package com.haici.googlecrx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * AuthSSLProtocolSocketFactory can be used to validate the identity of the HTTPS server against a list of trusted certificates and to authenticate to the HTTPS server using a private key.
 * </p>
 * 
 * <p>
 * AuthSSLProtocolSocketFactory will enable server authentication when supplied with a {@link KeyStore truststore} file containg one or several trusted certificates. The client secure socket will reject the connection during the SSL session handshake if the target HTTPS server attempts to authenticate itself with a non-trusted certificate.
 * </p>
 * 
 * <p>
 * Use JDK keytool utility to import a trusted certificate and generate a truststore file:
 * 
 * <pre>
 *     keytool -import -alias "my server cert" -file server.crt -keystore my.truststore
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * AuthSSLProtocolSocketFactory will enable client authentication when supplied with a {@link KeyStore keystore} file containg a private key/public certificate pair. The client secure socket will use the private key to authenticate itself to the target HTTPS server during the SSL session handshake if requested to do so by the server. The target HTTPS server will in its turn verify the certificate presented by the client in order to establish client's authenticity
 * </p>
 * 
 * <p>
 * Use the following sequence of actions to generate a keystore file
 * </p>
 * <ul>
 * <li>
 * <p>
 * Use JDK keytool utility to generate a new key
 * 
 * <pre>
 * keytool -genkey -v -alias "my client key" -validity 365 -keystore my.keystore
 * </pre>
 * 
 * For simplicity use the same password for the key as that of the keystore
 * </p>
 * </li>
 * <li>
 * <p>
 * Issue a certificate signing request (CSR)
 * 
 * <pre>
 * keytool -certreq -alias "my client key" -file mycertreq.csr -keystore my.keystore
 * </pre>
 * 
 * </p>
 * </li>
 * <li>
 * <p>
 * Send the certificate request to the trusted Certificate Authority for signature. One may choose to act as her own CA and sign the certificate request using a PKI tool, such as OpenSSL.
 * </p>
 * </li>
 * <li>
 * <p>
 * Import the trusted CA root certificate
 * 
 * <pre>
 * keytool -import -alias "my trusted ca" -file caroot.crt -keystore my.keystore
 * </pre>
 * 
 * </p>
 * </li>
 * <li>
 * <p>
 * Import the PKCS#7 file containg the complete certificate chain
 * 
 * <pre>
 * keytool -import -alias "my client key" -file mycert.p7 -keystore my.keystore
 * </pre>
 * 
 * </p>
 * </li>
 * <li>
 * <p>
 * Verify the content the resultant keystore file
 * 
 * <pre>
 * keytool -list -v -keystore my.keystore
 * </pre>
 * 
 * </p>
 * </li>
 * </ul>
 * <p>
 * Example of using custom protocol socket factory for a specific host:
 * 
 * <pre>
 * Protocol authhttps = new Protocol(&quot;https&quot;, new AuthSSLProtocolSocketFactory(new URL(&quot;file:my.keystore&quot;), &quot;mypassword&quot;, new URL(&quot;file:my.truststore&quot;), &quot;mypassword&quot;), 443);
 * 
 * HttpClient client = new HttpClient();
 * client.getHostConfiguration().setHost(&quot;localhost&quot;, 443, authhttps);
 * // use relative url only
 * GetMethod httpget = new GetMethod(&quot;/&quot;);
 * client.executeMethod(httpget);
 * </pre>
 * 
 * </p>
 * <p>
 * Example of using custom protocol socket factory per default instead of the standard one:
 * 
 * <pre>
 * Protocol authhttps = new Protocol(&quot;https&quot;, new AuthSSLProtocolSocketFactory(new URL(&quot;file:my.keystore&quot;), &quot;mypassword&quot;, new URL(&quot;file:my.truststore&quot;), &quot;mypassword&quot;), 443);
 * Protocol.registerProtocol(&quot;https&quot;, authhttps);
 * 
 * HttpClient client = new HttpClient();
 * GetMethod httpget = new GetMethod(&quot;https://localhost/&quot;);
 * client.executeMethod(httpget);
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:oleg -at- ural.ru">Oleg Kalnichevski</a>
 * 
 *         <p>
 *         DISCLAIMER: HttpClient developers DO NOT actively support this component. The component is provided as a reference material, which may be inappropriate for use without additional customization.
 *         </p>
 */

public class MultiHostTrustSSLProtocolSocketFactory extends SSLProtocolSocketFactory {

    private static class TrustSSL {
        HostTrustSSLConf conf;
        SSLContext sslContext;
        Set<String> trustHosts;

        public TrustSSL(HostTrustSSLConf conf, SSLContext sslContext, Set<String> trustHosts) {
            super();
            this.conf = conf;
            this.sslContext = sslContext;
            this.trustHosts = trustHosts;
        }
    }

    /** Log object for this class. */
    private static final Log LOG = LogFactory.getLog(MultiHostTrustSSLProtocolSocketFactory.class);

    Map<String, TrustSSL> trustSSLs;

    /**
     * Constructor for HostTrustSSLProtocolSocketFactory. Either a keystore or truststore file must be given. Otherwise SSL context initialization error will result.
     * 
     * @param trustHosts .
     * @param truststoreUrl URL of the truststore file. May be <tt>null</tt> if HTTPS server authentication is not to be used.
     * @param truststorePassword Password to unlock the truststore.
     */
    public MultiHostTrustSSLProtocolSocketFactory(Collection<HostTrustSSLConf> confs) {
        super();
        if (confs == null || confs.isEmpty()) {
            return;
        }
        this.trustSSLs = new HashMap<>();
        for (HostTrustSSLConf conf : confs) {
            TrustSSL t = this.createTrustSSL(conf);
            for (String host : t.trustHosts) {
                if (null != this.trustSSLs.put(host, t)) {
                    throw new IllegalArgumentException("Repeat host for diff keystore: " + host + "=" + t.conf.getTruststoreUrl());
                }
            }
        }
    }

    protected TrustSSL createTrustSSL(HostTrustSSLConf conf) {
        URL truststoreUrl = conf.getTruststoreUrl();
        String truststorePassword = conf.getTruststorePassword();
        SSLContext context = this.createSSLContext(truststoreUrl, truststorePassword);
        if (truststoreUrl == null) {
            LoggerFactory.getLogger(getClass()).warn("no trust store URL for [{}], will TrustAny", conf.getTrustHosts());
        }
        Collection<String> trustHosts = conf.getTrustHosts();
        Set<String> trustHostsSet;
        if (trustHosts == null || trustHosts.isEmpty()) {
            trustHostsSet = Collections.emptySet();
        } else {
            trustHostsSet = new HashSet<>();
            for (String s : trustHosts) {
                s = StringUtils.trimToNull(s);
                if (s != null) {
                    int i = 0;
                    char c = s.charAt(i);
                    while (c == '*' || c == '.') {
                        i++;
                        c = s.charAt(i);
                    }
                    if (i != s.length()) {
                        trustHostsSet.add(s.substring(i));
                    }
                }
            }
        }
        return new TrustSSL(conf, context, trustHostsSet);
    }

    private static KeyStore createKeyStore(final URL url, final String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        if (url == null) {
            throw new IllegalArgumentException("Keystore url may not be null");
        }
        LOG.debug("Initializing key store");
        KeyStore keystore = KeyStore.getInstance("jks");
        InputStream is = null;
        try {
            is = url.openStream();
            keystore.load(is, password != null ? password.toCharArray() : null);
        } finally {
            if (is != null)
                is.close();
        }
        return keystore;
    }

    // private static KeyManager[] createKeyManagers(final KeyStore keystore,
    // final String password) throws KeyStoreException,
    // NoSuchAlgorithmException, UnrecoverableKeyException {
    // if (keystore == null) {
    // throw new IllegalArgumentException("Keystore may not be null");
    // }
    // LOG.debug("Initializing key manager");
    // KeyManagerFactory kmfactory = KeyManagerFactory
    // .getInstance(KeyManagerFactory.getDefaultAlgorithm());
    // kmfactory.init(keystore, password != null ? password.toCharArray()
    // : null);
    // return kmfactory.getKeyManagers();
    // }

    private static TrustManager[] createTrustManagers(final KeyStore keystore) throws KeyStoreException, NoSuchAlgorithmException {
        if (keystore == null) {
            throw new IllegalArgumentException("Keystore may not be null");
        }
        LOG.debug("Initializing trust manager");
        TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmfactory.init(keystore);
        TrustManager[] trustmanagers = tmfactory.getTrustManagers();
        for (int i = 0; i < trustmanagers.length; i++) {
            if (trustmanagers[i] instanceof X509TrustManager) {
                trustmanagers[i] = new AuthSSLX509TrustManager((X509TrustManager) trustmanagers[i]);
            }
        }
        return trustmanagers;
    }

    private SSLContext createSSLContext(URL truststoreUrl, String truststorePassword) {
        try {
            TrustManager[] trustmanagers = null;
            if (truststoreUrl != null) {
                KeyStore keystore = createKeyStore(truststoreUrl, truststorePassword);
                trustmanagers = createTrustManagers(keystore);
            } else {
                trustmanagers = new TrustManager[] { TrustAnyTrustManager.def };
            }
            SSLContext sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null, trustmanagers, null);
            return sslcontext;
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getMessage(), e);
            throw new AuthSSLInitializationError("Unsupported algorithm exception: " + e.getMessage());
        } catch (KeyStoreException e) {
            LOG.error(e.getMessage(), e);
            throw new AuthSSLInitializationError("Keystore exception: " + e.getMessage());
        } catch (GeneralSecurityException e) {
            LOG.error(e.getMessage(), e);
            throw new AuthSSLInitializationError("Key management exception: " + e.getMessage());
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new AuthSSLInitializationError("I/O error reading keystore/truststore file: " + e.getMessage());
        }
    }

    /**
     * Attempts to get a new socket connection to the given host within the given time limit.
     * <p>
     * To circumvent the limitations of older JREs that do not support connect timeout a controller thread is executed. The controller thread attempts to create a new socket within the given limit of time. If socket constructor does not return until the timeout expires, the controller terminates and throws an {@link ConnectTimeoutException}
     * </p>
     * 
     * @param host the host name/IP
     * @param port the port on the host
     * @param clientHost the local host name/IP to bind the socket to
     * @param clientPort the port on the local machine
     * @param params {@link HttpConnectionParams Http connection parameters}
     * 
     * @return Socket a new socket
     * 
     * @throws IOException if an I/O error occurs while creating the socket
     * @throws UnknownHostException if the IP address of the host cannot be determined
     */
    @Override
    public Socket createSocket(final String host, final int port, final InetAddress localAddress, final int localPort, final HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
        TrustSSL trustSSL = handleHost(host);
        if (trustSSL == null) {
            return super.createSocket(host, port, localAddress, localPort, params);
        }
        if (params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }
        int timeout = params.getConnectionTimeout();
        SocketFactory socketfactory = trustSSL.sslContext.getSocketFactory();
        if (timeout == 0) {
            return socketfactory.createSocket(host, port, localAddress, localPort);
        } else {
            Socket socket = socketfactory.createSocket();
            SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
            SocketAddress remoteaddr = new InetSocketAddress(host, port);
            socket.bind(localaddr);
            socket.connect(remoteaddr, timeout);
            return socket;
        }
    }

    private TrustSSL handleHost(String host) {
        while (host != null) {
            TrustSSL ssl = this.trustSSLs.get(host);
            if (ssl != null) {
                return ssl;
            }
            int i = host.indexOf('.');
            if (i > -1) {
                host = host.substring(i + 1);
            } else {
                host = null;
            }
        }
        return null;
    }

    /**
     * @see SecureProtocolSocketFactory#createSocket(java.lang.String,int,java.net.InetAddress,int)
     */
    @Override
    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException, UnknownHostException {
        TrustSSL trustSSL = handleHost(host);
        if (trustSSL == null) {
            return super.createSocket(host, port, clientHost, clientPort);
        }
        return trustSSL.sslContext.getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }

    /**
     * @see SecureProtocolSocketFactory#createSocket(java.lang.String,int)
     */
    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        TrustSSL trustSSL = handleHost(host);
        if (trustSSL == null) {
            return super.createSocket(host, port);
        }
        return trustSSL.sslContext.getSocketFactory().createSocket(host, port);
    }

    /**
     * @see SecureProtocolSocketFactory#createSocket(java.net.Socket,java.lang.String,int,boolean)
     */
    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
        TrustSSL trustSSL = handleHost(host);
        if (trustSSL == null) {
            return super.createSocket(socket, host, port, autoClose);
        }
        return trustSSL.sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }
}
