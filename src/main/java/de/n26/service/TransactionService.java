package de.n26.service;


import de.n26.dao.TransactionsDAO;
import de.n26.exception.*;
import de.n26.model.Transaction;
import de.n26.model.TransactionSum;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/transactionservice")
public class TransactionService {

    @PUT
    @Path("/transaction/{transaction_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTransaction(@PathParam("transaction_id") Long transactionId, Transaction transaction) throws ExistingTransactionException, IllegalTransactionFormatException, IllegalTransactionParentIDException {

        if (transaction.getAmount() == null || transaction.getType() == null || transaction.getType().isEmpty())
            throw new IllegalTransactionFormatException("Argument Transaction format is not valid ");

        transaction.setId(transactionId);
        TransactionsDAO.getInstance().addTransaction(transaction);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/transaction/{transaction_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction getTransaction(@PathParam("transaction_id") Long transactionId) throws NonExistingTransactionException {

        return TransactionsDAO.getInstance().getTransaction(transactionId);
    }

    @GET
    @Path("/types/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Long> getTransactionsByType(@PathParam("type") String type) throws NonExistingTransactionTypeException {

        return TransactionsDAO.getInstance().getTransactionsByType(type);
    }


    @GET
    @Path("/sum/{transaction_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TransactionSum getTransactionsSumByParentId(@PathParam("transaction_id") Long transactionId) throws NonExistingTransactionException {
        return TransactionsDAO.getInstance().getTransactionsSumByParentId(transactionId);
    }
}
