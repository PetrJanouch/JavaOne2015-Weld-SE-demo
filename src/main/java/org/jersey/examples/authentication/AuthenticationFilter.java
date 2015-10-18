package org.jersey.examples.authentication;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import javax.annotation.Priority;
import javax.inject.Inject;


@Priority(50)
@FormAuthenticated
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private User user;

    @Inject
    private OriginalDestination originalDestination;

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        if (!user.isAuthenticated()) {
            if (originalDestination.getUri() == null) {
                originalDestination.setUri(requestContext.getUriInfo().getRequestUri());
            }

            requestContext.abortWith(Response.seeOther(UriBuilder.fromResource(Authenticator.class).build()).build());
        }
    }
}
