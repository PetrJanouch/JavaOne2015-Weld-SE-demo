package org.jersey.examples.authentication;

import java.net.URI;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;

import org.jboss.weld.environment.se.Weld;

@ApplicationPath("/*")
public class MyApp extends ResourceConfig {

    public static final URI AppURI = URI.create("http://localhost:8080/app");

    public MyApp() {
        super(Authenticator.class, HelloResource.class, AuthenticationFilter.class, SessionFilter.class);
        register(FreemarkerMvcFeature.class);
    }

    public static void main(String[] args) throws Exception {

        URI myUri = (args.length > 0) ? URI.create(args[0]) : AppURI;

        Weld weld = new Weld();
        weld.initialize();

        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(myUri, new MyApp(), false);
        HttpHandler httpHandler = new CLStaticHttpHandler(HttpServer.class.getClassLoader(), "/web/");
        httpServer.getServerConfiguration().addHttpHandler(httpHandler, "/");
        httpServer.start();
        System.in.read();
        httpServer.shutdown();
        weld.shutdown();
    }
}
