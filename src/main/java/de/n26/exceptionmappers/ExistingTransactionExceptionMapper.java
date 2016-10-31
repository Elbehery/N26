package de.n26.exceptionmappers;


import de.n26.exception.ExistingTransactionException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExistingTransactionExceptionMapper implements ExceptionMapper<ExistingTransactionException> {

    @Override
    public Response toResponse(ExistingTransactionException exception) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(exception.getMessage())
                .build();
    }
}
