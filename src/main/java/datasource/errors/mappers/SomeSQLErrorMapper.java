package datasource.errors.mappers;

import datasource.errors.SomeSQLError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SomeSQLErrorMapper implements ExceptionMapper<SomeSQLError> {
    @Override
    public Response toResponse(SomeSQLError someSQLError) {
        return Response.status(400).entity(someSQLError).build();
    }
}
