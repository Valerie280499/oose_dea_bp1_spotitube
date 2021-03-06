package controllers;

import dto.LoginRequestDTO;
import services.LoginService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController {
    private LoginService loginService;

    @Inject
    public void setLoginService(LoginService loginService){
        this.loginService = loginService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO){
            var login = loginService.login(loginRequestDTO);

            return Response.ok().entity(login).build();
    }

}

// https://jenkins.aimsites.nl/spotitube/
