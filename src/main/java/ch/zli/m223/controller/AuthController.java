package ch.zli.m223.controller;

import ch.zli.m223.repository.ApplicationUserRepository;
import ch.zli.m223.model.ApplicationUser;

import DTO.LoginDto;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/auth")
public class AuthController {

    @Inject
    ApplicationUserRepository applicationUserRepository;
    
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response generateToken(@Valid LoginDto loginDto) {

        ApplicationUser user = applicationUserRepository.findApplicationUserByEmail(loginDto.getEmail());

        if (user == null || !BcryptUtil.matches(loginDto.getPassword(), user.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String token = Jwt.upn(user.getEmail())
                        .issuer("https://example.com/issuer")
                        .groups(Set.of("User"))
                        .sign();

        return Response.ok(token).build();

    }
}
