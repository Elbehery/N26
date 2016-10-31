package de.n26.dao;

import de.n26.exception.ExistingTransactionException;
import de.n26.exception.IllegalTransactionParentIDException;
import de.n26.exception.NonExistingTransactionException;
import de.n26.exception.NonExistingTransactionTypeException;
import de.n26.model.Transaction;
import de.n26.model.TransactionSum;
import de.n26.persistence.MemoryTransactions;
import de.n26.persistence.MemoryTransactionsImpl;

import java.util.List;


public class TransactionsDAO {

    private MemoryTransactions memoryTransactions;
    private static TransactionsDAO transactionsDAO = null;

    private TransactionsDAO() {
        this.memoryTransactions = new MemoryTransactionsImpl();
    }

    public static synchronized TransactionsDAO getInstance() {
        if (transactionsDAO == null) {
            transactionsDAO = new TransactionsDAO();
        }

        return transactionsDAO;
    }


    public void addTransaction(Transaction transaction) throws ExistingTransactionException, IllegalTransactionParentIDException {
        this.memoryTransactions.addTransaction(transaction);
    }


    public Transaction getTransaction(Long transactionId) throws NonExistingTransactionException {
        return this.memoryTransactions.getTransaction(transactionId);
    }


    public List<Long> getTransactionsByType(String type) throws NonExistingTransactionTypeException {
        return this.memoryTransactions.getTransactionsByType(type);
    }


    public TransactionSum getTransactionsSumByParentId(Long transactionId) throws NonExistingTransactionException {
        return this.memoryTransactions.getTransactionsSumByParentId(transactionId);
    }
}
