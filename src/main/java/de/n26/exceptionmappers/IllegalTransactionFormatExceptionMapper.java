package de.n26.exceptionmappers;

import de.n26.exception.IllegalTransactionFormatException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalTransactionFormatExceptionMapper implements ExceptionMapper<IllegalTransactionFormatException> {

    @Override
    public Response toResponse(IllegalTransactionFormatException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .build();
    }


}
