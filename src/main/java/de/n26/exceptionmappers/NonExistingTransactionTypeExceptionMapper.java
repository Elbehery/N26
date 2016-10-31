package de.n26.exceptionmappers;

import de.n26.exception.NonExistingTransactionTypeException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NonExistingTransactionTypeExceptionMapper implements ExceptionMapper<NonExistingTransactionTypeException> {
    @Override
    public Response toResponse(NonExistingTransactionTypeException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(exception)
                .build();
    }
}
