package com.brocode.apply;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/")
public class GreetingService {
    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public String getGreeting() {
        return "{\"greeting\": \"Hello World!\"}";
    }
}
