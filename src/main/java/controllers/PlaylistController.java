package controllers;

import datasource.DAO.PlaylistDAO;
import dto.PlaylistDTO;
import dto.TrackDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistController{
    private static final String SELECT_TRACKS_FROM_PLAYLIST = "SELECT T.* FROM track T INNER JOIN playlistTracks P ON T.id = P.idTrack WHERE P.idPlaylist = ?";

    @Inject
    protected PlaylistDAO playlistDAO = new PlaylistDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }
        var playlistsDTO = playlistDAO.getAllPlaylists();

        return Response.ok().entity(playlistsDTO).build();
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksInAPlaylist(@PathParam("id") int playlist_id, @QueryParam("token") String token){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }
        var tracksDTO = playlistDAO.getTracks(playlist_id, SELECT_TRACKS_FROM_PLAYLIST);

        return Response.ok().entity(tracksDTO).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO newPlaylist){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }
        var playlistsDTO = playlistDAO.addPlaylist(newPlaylist);

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

        playlistDAO.addTrackToPlaylist(playlist_id, newTrack);
        var tracksDTO = playlistDAO.getTracks(playlist_id, SELECT_TRACKS_FROM_PLAYLIST);

        return Response.ok().entity(tracksDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int playlist_id, @QueryParam("token") String token){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }
        var playlistsDTO = playlistDAO.deletePlaylist(playlist_id);

        return Response.ok().entity(playlistsDTO).build();

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") String token, PlaylistDTO newPlaylist){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }
        var playlistsDTO = playlistDAO.editPlaylist(newPlaylist);

        return Response.ok().entity(playlistsDTO).build();
    }

    @DELETE
    @Path("/{id}/tracks/{track_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@PathParam("id") int playlist_id, @PathParam("track_id") int track_id, @QueryParam("token") String token){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }

        playlistDAO.deleteTrackFromPlaylist(playlist_id, track_id);
        var tracksDTO = playlistDAO.getTracks(playlist_id, SELECT_TRACKS_FROM_PLAYLIST);

        return Response.ok().entity(tracksDTO).build();
    }
}
