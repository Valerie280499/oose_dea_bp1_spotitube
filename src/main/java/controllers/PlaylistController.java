package controllers;

import controllers.DTO.PlaylistDTO;
import controllers.DTO.PlaylistsDTO;
import service.PlaylistService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/playlists")
public class PlaylistController{
    private PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    public PlaylistController() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response playlist(@QueryParam("token") String token){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }
        var playlistsDTO = playlistService.generatePlaylists();

        return Response.ok().entity(playlistsDTO).build();
    }
}
