package de.n26.exceptionmappers;


import de.n26.exception.IllegalTransactionParentIDException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class IllegalTransactionParentIDExceptionMapper implements ExceptionMapper<IllegalTransactionParentIDException> {

    @Override
    public Response toResponse(IllegalTransactionParentIDException exception) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(exception.getMessage())
                .build();
    }
}
