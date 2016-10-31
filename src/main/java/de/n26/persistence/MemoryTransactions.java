package de.n26.persistence;


import de.n26.exception.ExistingTransactionException;
import de.n26.exception.IllegalTransactionParentIDException;
import de.n26.exception.NonExistingTransactionException;
import de.n26.exception.NonExistingTransactionTypeException;
import de.n26.model.Transaction;
import de.n26.model.TransactionSum;

import java.util.List;

public interface MemoryTransactions {

    void addTransaction(Transaction transaction) throws ExistingTransactionException, IllegalTransactionParentIDException;

    Transaction getTransaction(Long transactionId) throws NonExistingTransactionException;

    List<Long> getTransactionsByType(String type) throws NonExistingTransactionTypeException;

    TransactionSum getTransactionsSumByParentId(Long transactionId) throws NonExistingTransactionException;
}
