package org.jersey.examples.authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.glassfish.jersey.server.mvc.Viewable;


@ApplicationScoped
@Path("authentication")
public class Authenticator {

    private final Map<String, String> credentials = new ConcurrentHashMap<>();

    @Inject
    private OriginalDestination originalDestination;

    @Inject
    private User currentUser;

    @PostConstruct
    public void fillCredentials() {
        credentials.put("admin", "admin");
        credentials.put("petr", "secret password");
        credentials.put("jersey", "jersey");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getLoginPage() {
        Map<String, Object> binding = new HashMap<>();
        binding.put("users", credentials);

        return new Viewable("/freemarker/authentication", binding);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public Object handleLogin(@FormParam("username") String userName, @FormParam("password") String password) {
        if (authenticate(userName, password)) {
            return Response.seeOther(originalDestination.getUri()).build();
        }

        Map<String, Object> binding = new HashMap<>();
        binding.put("users", credentials);
        binding.put("errorMessage", "Invalid username and password combination");

        return new Viewable("/freemarker/authentication", binding);
    }

    private boolean authenticate(String userName, String password) {
        String pass = credentials.get(userName);
        if (!password.equals(pass)) {
            return false;
        }

        currentUser.setName(userName);
        currentUser.setAuthenticated(true);
        return true;
    }
}
