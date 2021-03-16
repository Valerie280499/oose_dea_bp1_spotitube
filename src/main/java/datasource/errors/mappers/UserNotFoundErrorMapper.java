package datasource.errors.mappers;

import datasource.errors.UserNotFoundError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserNotFoundErrorMapper implements ExceptionMapper<UserNotFoundError> {
    @Override
    public Response toResponse(UserNotFoundError userNotFoundError) {
        return Response.status(400).entity(userNotFoundError).build();
    }
}
