package org.jersey.examples.authentication;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.glassfish.jersey.server.mvc.Viewable;

@RequestScoped
@Path("/hello")
public class HelloResource {

    @Inject
    private User user;

    @FormAuthenticated
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable sayHello() {
        Map<String, String> binding = new HashMap<>();
        binding.put("user", user.getName());
        return new Viewable("/freemarker/hello", binding);
    }
}
