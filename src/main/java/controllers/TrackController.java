package controllers;

import datasource.dao.TrackDAO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
public class TrackController {
    private TrackDAO trackDAO;

    @Inject public void setTrackDAO(TrackDAO trackDAO){ this.trackDAO = trackDAO;}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlist_id){
        var tracksDTO = trackDAO.getTracksWhichAreNotInAPlaylist(token, playlist_id);
        return Response.ok().entity(tracksDTO).build();
    }
}
