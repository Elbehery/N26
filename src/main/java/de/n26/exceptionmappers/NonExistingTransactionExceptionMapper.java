package de.n26.exceptionmappers;

import de.n26.exception.NonExistingTransactionException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NonExistingTransactionExceptionMapper implements ExceptionMapper<NonExistingTransactionException> {

    @Override
    public Response toResponse(NonExistingTransactionException exception) {

        return Response.status(Response.Status.NOT_FOUND)
                .entity(exception.getMessage())
                .build();
    }
}
