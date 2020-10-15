package controllers.rest;


import service.TrackService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/tracks")
public class TrackController {
    private TrackService trackService;

    @Inject
    public void setTrackService(TrackService trackService){this.trackService = trackService;}

    @GET
    public Response getAllTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlist_id){
        if (!token.equals("Hello")){
            return Response.status(401).build();
        }

        var tracksDTO = trackService.generateTracks();

        return Response.ok().entity(tracksDTO).build();
    }
}
