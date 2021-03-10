package controllers;


import datasource.DAO.PlaylistDAO;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
public class TrackController {
    private static final String SELECT_TRACKS_FROM_TRACK = "SELECT T.* FROM track T where id not in (select P.idTrack FROM playlistTracks P WHERE P.idPlaylist = ?)";
    private PlaylistDAO playlistDAO;

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO){
        this.playlistDAO = playlistDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlist_id){
        var tracksDTO = playlistDAO.getTracks(token, playlist_id, SELECT_TRACKS_FROM_TRACK);
        return Response.ok().entity(tracksDTO).build();
    }
}
