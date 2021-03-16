package datasource.errors.mappers;

import datasource.errors.PlayListNotFoundError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlaylistNotfoundErrorMapper implements ExceptionMapper<PlayListNotFoundError> {
    @Override
    public Response toResponse(PlayListNotFoundError playListNotFoundError) {
        return Response.status(400).entity(playListNotFoundError).build();
    }
}
