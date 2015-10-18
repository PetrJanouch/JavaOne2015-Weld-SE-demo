package org.jersey.examples.authentication;

import java.io.Serializable;
import java.net.URI;

import javax.enterprise.context.RequestScoped;

import org.glassfish.grizzly.http.server.Session;

@RequestScoped
public class OriginalDestination implements Serializable {

    private static final String KEY = "original destination";

    private URI uri;

    public URI getUri() {
        return uri;
    }

    public void setUri(final URI uri) {
        this.uri = uri;
    }

    public void storeInSession(Session session) {
        session.setAttribute(KEY, this);
    }

    static void fillFromSession(OriginalDestination originalDestination, Session session) {
        OriginalDestination storedDestination = (OriginalDestination) session.getAttribute(KEY);

        if (storedDestination != null) {
            originalDestination.setUri(storedDestination.getUri());
        }
    }
}
