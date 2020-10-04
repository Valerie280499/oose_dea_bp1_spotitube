package controllers;

import service.PlaylistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistController{
    private PlaylistService playlistService;


    @Inject
    public void setPlaylistService(PlaylistService playlistService){
        this.playlistService = playlistService;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPlaylists(@QueryParam("token") String token){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }
        var playlistsDTO = playlistService.generatePlaylists();

        return Response.ok().entity(playlistsDTO).build();
    }
}
