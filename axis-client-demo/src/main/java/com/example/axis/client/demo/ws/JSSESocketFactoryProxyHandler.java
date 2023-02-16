package com.example.axis.client.demo.ws;

import org.apache.axis.components.net.DefaultSocketFactory;
import org.apache.axis.components.net.JSSESocketFactory;
import org.apache.axis.components.net.TransportClientProperties;
import org.apache.axis.components.net.TransportClientPropertiesFactory;
import org.apache.axis.utils.Messages;
import org.apache.axis.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.ssl.SSLSocketFactoryImpl;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author lirh
 * @since 2023/2/1 11:11
 */
public class JSSESocketFactoryProxyHandler implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSSESocketFactoryProxyHandler.class);

    private JSSESocketFactory socketFactory;
    private SSLSocketFactory sslFactory;
    private int timeout;

    public JSSESocketFactoryProxyHandler(JSSESocketFactory socketFactory, int timeout) {
        this.socketFactory = socketFactory;
        this.timeout = timeout;
    }

    private void initFactory() throws Exception {
        try {
            Method initMethod = JSSESocketFactory.class.getDeclaredMethod("initFactory");
            initMethod.setAccessible(true);
            initMethod.invoke(socketFactory);
            Field field = JSSESocketFactory.class.getDeclaredField("sslFactory");
            field.setAccessible(true);
            sslFactory = (SSLSocketFactory) field.get(socketFactory);
        } catch (Exception e) {
            LOGGER.error("Apache axis JSSESocketFactory initialization failed", e);
            throw e;
        }
    }

    protected boolean isHostInNonProxyList(String host, String nonProxyHosts) {
        try {
            Method method = DefaultSocketFactory.class
                    .getDeclaredMethod("isHostInNonProxyList", String.class, String.class);
            method.setAccessible(true);
            return (Boolean) method.invoke(socketFactory, host, nonProxyHosts);
        } catch (Exception e) {
            LOGGER.error("Apache axis JSSESocketFactory isHostInNonProxyList reflection failed", e);
            return false;
        }
    }

    //创建一个socket,要处于连接状态,要设置好超时时间
    private Socket newSocketAndConnect(String host, int port)
            throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), timeout);
        socket.setSoTimeout(timeout);
        return socket;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String host = (String) args[0];
        int port = (Integer) args[1];

        //********************* 以下copy 自 JSSESocketFactory的create方法**************

        if (sslFactory == null) {
            initFactory();
        }
        if (port == -1) {
            port = 443;
        }

        TransportClientProperties tcp = TransportClientPropertiesFactory.create("https");

        boolean hostInNonProxyList = isHostInNonProxyList(host, tcp.getNonProxyHosts());

        Socket sslSocket = null;
        if (tcp.getProxyHost().length() == 0 || hostInNonProxyList) {
            // direct SSL connection
            if (sslFactory instanceof SSLSocketFactoryImpl) {

                //public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
                sslSocket = sslFactory.createSocket(newSocketAndConnect(host, port), host, port, true);
            } else {
                sslSocket = sslFactory.createSocket(host, port);
            }
        } else {

            // Default proxy port is 80, even for https
            int tunnelPort = (tcp.getProxyPort().length() != 0)
                    ? Integer.parseInt(tcp.getProxyPort())
                    : 80;
            if (tunnelPort < 0) {
                tunnelPort = 80;
            }

            // Create the regular socket connection to the proxy
            Socket tunnel = new Socket(tcp.getProxyHost(), tunnelPort);

            // The tunnel handshake method (condensed and made reflexive)
            OutputStream tunnelOutputStream = tunnel.getOutputStream();
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(tunnelOutputStream)));

            out.print("CONNECT " + host + ":" + port + " HTTP/1.0\r\n"
                    + "User-Agent: AxisClient");
            if (tcp.getProxyUser().length() != 0 &&
                    tcp.getProxyPassword().length() != 0) {

                // add basic authentication header for the proxy
                String encodedPassword = XMLUtils.base64encode((tcp.getProxyUser()
                        + ":"
                        + tcp.getProxyPassword()).getBytes());

                out.print("\nProxy-Authorization: Basic " + encodedPassword);
            }
            out.print("\nContent-Length: 0");
            out.print("\nPragma: no-cache");
            out.print("\r\n\r\n");
            out.flush();
            InputStream tunnelInputStream = tunnel.getInputStream();

            String replyStr = "";

            // Make sure to read all the response from the proxy to prevent SSL negotiation failure
            // Response message terminated by two sequential newlines
            int newlinesSeen = 0;
            boolean headerDone = false;    /* Done on first newline */

            while (newlinesSeen < 2) {
                int i = tunnelInputStream.read();

                if (i < 0) {
                    throw new IOException("Unexpected EOF from proxy");
                }
                if (i == '\n') {
                    headerDone = true;
                    ++newlinesSeen;
                } else if (i != '\r') {
                    newlinesSeen = 0;
                    if (!headerDone) {
                        replyStr += String.valueOf((char) i);
                    }
                }
            }
            if (org.apache.axis.utils.StringUtils.startsWithIgnoreWhitespaces("HTTP/1.0 200", replyStr) &&
                    org.apache.axis.utils.StringUtils.startsWithIgnoreWhitespaces("HTTP/1.1 200", replyStr)) {
                throw new IOException(Messages.getMessage("cantTunnel00",
                        new String[] {
                                tcp.getProxyHost(),
                                "" + tunnelPort,
                                replyStr }));
            }

            // End of condensed reflective tunnel handshake method
            sslSocket = sslFactory.createSocket(tunnel, host, port, true);
        }

        ((SSLSocket) sslSocket).startHandshake();

        return sslSocket;
    }

}
