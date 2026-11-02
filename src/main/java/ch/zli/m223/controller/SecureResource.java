package ch.zli.m223.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/secure")
public class SecureResource {

    @GET
    @Path("/data")
    @RolesAllowed("User")
    @Produces(MediaType.TEXT_PLAIN)
    public String getSecureData() {
        return "This is protected data.";
    }
}
