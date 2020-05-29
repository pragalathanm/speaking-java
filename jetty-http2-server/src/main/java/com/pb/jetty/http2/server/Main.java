package com.pb.jetty.http2.server;

import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 *
 * @author Pragalathan M
 */
public class Main {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    public static void main(String[] args) throws Exception {
        Server server = new Server();

        // HTTP connector
        HttpConfiguration http2Config = new HttpConfiguration();
        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(), new HTTP2CServerConnectionFactory(http2Config));
        http.setHost(HOST);
        http.setPort(PORT);
        http.setIdleTimeout(30_000);
        // Set the connector
        server.addConnector(http);

        // set servlet handler
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        ServletHolder asyncHolder = context.addServlet(HelloServlet.class, "/hello-servlet");
        server.setHandler(context);

        server.start();
        server.join();
    }
}
