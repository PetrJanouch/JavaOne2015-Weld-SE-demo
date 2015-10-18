package org.jersey.examples.authentication;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;

import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Session;


@Priority(40)
public class SessionFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Inject
    private User user;

    @Inject
    private OriginalDestination originalDestination;

    @Inject
    private Session session;

    @Inject
    private ServiceLocator serviceLocator;

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
       User.fillFromSession(user, session);
        OriginalDestination.fillFromSession(originalDestination, session);
    }



    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
            throws IOException {

        if (user.isAuthenticated()) {
            user.storeInSession(session);
        } else {
            if (originalDestination.getUri() != null) {
                originalDestination.storeInSession(session);
            }
        }
    }

    @RequestScoped
    @Produces
    public  Session getSession() {
        Request request = serviceLocator.getService(Request.class);
        return request.getSession();
    }


}
