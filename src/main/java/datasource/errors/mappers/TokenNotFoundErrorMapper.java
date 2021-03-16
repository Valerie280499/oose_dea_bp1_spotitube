package datasource.errors.mappers;

import datasource.errors.TokenNotFoundError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TokenNotFoundErrorMapper implements ExceptionMapper<TokenNotFoundError> {
    @Override
    public Response toResponse(TokenNotFoundError tokenNotFoundError) {
        return Response.status(400).entity(tokenNotFoundError).build();
    }
}
