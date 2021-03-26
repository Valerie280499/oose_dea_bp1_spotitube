package controllers;

import datasource.dao.PlaylistDAO;
import datasource.dao.UserDAO;
import dto.PlaylistDTO;
import dto.TrackDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistController{
    private PlaylistDAO playlistDAO;
    private UserDAO userDAO;

    @Inject public void setPlaylistDAO(PlaylistDAO playlistDAO){
        this.playlistDAO = playlistDAO;
    }

    @Inject public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token){
        userDAO.getUserByToken(token);

        var playlistsDTO = playlistDAO.getAllPlaylists();

        return Response.ok().entity(playlistsDTO).build();
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksInAPlaylist(@PathParam("id") int playlist_id, @QueryParam("token") String token){
        userDAO.getUserByToken(token);

        var tracksDTO = playlistDAO.getTracksInPlaylist(playlist_id);

        return Response.ok().entity(tracksDTO).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO newPlaylist){
        userDAO.getUserByToken(token);

        var playlistsDTO = playlistDAO.addPlaylist(newPlaylist);

        return Response.ok().entity(playlistsDTO).build();
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("id") int playlist_id, @QueryParam("token") String token, TrackDTO newTrack){
        userDAO.getUserByToken(token);

        playlistDAO.addTrackToPlaylist(playlist_id, newTrack);
        var tracksDTO = playlistDAO.getTracksInPlaylist(playlist_id);

        return Response.ok().entity(tracksDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int playlist_id, @QueryParam("token") String token){
        userDAO.getUserByToken(token);

        var playlistsDTO = playlistDAO.deletePlaylist(playlist_id);

        return Response.ok().entity(playlistsDTO).build();

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") String token, PlaylistDTO newPlaylist){
        userDAO.getUserByToken(token);

        var playlistsDTO = playlistDAO.editPlaylist(newPlaylist);

        return Response.ok().entity(playlistsDTO).build();
    }

    @DELETE
    @Path("/{id}/tracks/{track_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@PathParam("id") int playlist_id, @PathParam("track_id") int track_id, @QueryParam("token") String token){
        userDAO.getUserByToken(token);

        playlistDAO.deleteTrackFromPlaylist(track_id);
        var tracksDTO = playlistDAO.getTracksInPlaylist(playlist_id);

        return Response.ok().entity(tracksDTO).build();
    }
}
