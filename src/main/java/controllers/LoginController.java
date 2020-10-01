package controllers;

import controllers.DTO.LoginRequestDTO;
import controllers.DTO.LoginResponseDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO){
        if ("valerie".equals(loginRequestDTO.getUser()) && "blaat".equals(loginRequestDTO.getPassword())){
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setToken("Hello");
            loginResponseDTO.setUser("jack");

            return Response.ok().entity(loginResponseDTO).build();
        } else{
            return Response.status(401).build();
        }
    }
}
