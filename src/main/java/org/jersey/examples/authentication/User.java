package org.jersey.examples.authentication;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

import org.glassfish.grizzly.http.server.Session;

@RequestScoped
public class User implements Serializable {

    private static final String KEY = "user";

    private String name;
    private boolean authenticated = false;

    public User() {
        System.out.println("User created");
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(final boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getName() {
        if (!authenticated) {
            throw new IllegalStateException("User is not logged in");
        }
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void storeInSession(Session session) {
        session.setAttribute(KEY, this);
    }

    static void fillFromSession(User user, Session session) {
        User storedUser = (User) session.getAttribute(KEY);

        if (storedUser != null) {
            user.setName(storedUser.getName());
            user.setAuthenticated(storedUser.isAuthenticated());
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("post construct");
    }
}
