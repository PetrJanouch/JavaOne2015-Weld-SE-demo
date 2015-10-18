package org.jersey.examples.authentication.old;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jersey.examples.authentication.Authenticator;

/**
 * Created by petr on 27/09/15.
 */
@FormAuthenticatedOld
@Interceptor
public class FormAuthenticationInterceptorOld {

//    @Inject
//    private User user;
//
//    @Inject
//    private OriginalDestination originalDestination;



    @AroundInvoke
    public Object checkUserAuthenticated(InvocationContext ctx) throws Exception {
//        if (user.isAuthenticated()) {
//            return ctx.proceed();
//        }

      //  originalDestination.setUri(request.getRequestUri());

        return Response.seeOther(UriBuilder.fromResource(Authenticator.class).build()).build();
    }
}
