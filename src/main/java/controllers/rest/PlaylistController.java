package controllers.rest;

import controllers.DTO.playlist.PlaylistDTO;
import controllers.DTO.track.TrackDTO;
import service.PlaylistService;
import service.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistController{
    private PlaylistService playlistService;
    private TrackService trackService;

    @Inject
    public void setPlaylistService(PlaylistService playlistService){
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackService(TrackService trackService){ this.trackService = trackService;}


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPlaylists(@QueryParam("token") String token){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }
        var playlistsDTO = playlistService.generatePlaylists();

        return Response.ok().entity(playlistsDTO).build();
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksInAPlaylist(@PathParam("id") int playlist_id, @QueryParam("token") String token){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }

        var tracksDTO = trackService.getAllTracksInAPlaylist(playlist_id);

        return Response.ok().entity(tracksDTO).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO newPlaylist){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }
        var playlistsDTO = playlistService.addPlaylist(newPlaylist);

        return Response.ok().entity(playlistsDTO).build();
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("id") int playlist_id, @QueryParam("token") String token, TrackDTO newTrack){
        if (!token.equals("Hallo")){
            return Response.status(401).build();
        }
        var tracksDTO = trackService.addTrackToPlaylist(playlist_id, newTrack);

        return Response.ok().entity(tracksDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int playlist_id, @QueryParam("token") String token){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }

        var playlistsDTO = playlistService.deleteAPlaylist(playlist_id);

        return Response.ok().entity(playlistsDTO).build();

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@PathParam("id") int playlist_id, @QueryParam("token") String token, PlaylistDTO newPlaylist){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }

        var playlistsDTO = playlistService.editPlayist(playlist_id, newPlaylist);

        return Response.ok().entity(playlistsDTO).build();
    }




}
