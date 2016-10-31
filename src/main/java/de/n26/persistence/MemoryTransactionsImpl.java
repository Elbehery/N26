package de.n26.persistence;


import de.n26.exception.ExistingTransactionException;
import de.n26.exception.IllegalTransactionParentIDException;
import de.n26.exception.NonExistingTransactionException;
import de.n26.exception.NonExistingTransactionTypeException;
import de.n26.model.Transaction;
import de.n26.model.TransactionSum;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryTransactionsImpl implements MemoryTransactions {

    private Map<Long, Transaction> transactionsMap;
    private Map<String, List<Long>> transactionsByTypeMap;
    private Map<Long, List<Long>> transactionsByParentIdMap;

    public MemoryTransactionsImpl() {
        this.transactionsMap = new ConcurrentHashMap<>();
        this.transactionsByTypeMap = new ConcurrentHashMap<>();
        this.transactionsByParentIdMap = new ConcurrentHashMap<>();
    }

    @Override
    public void addTransaction(Transaction transaction) throws ExistingTransactionException, IllegalTransactionParentIDException {

        if (this.transactionsMap.containsKey(transaction.getId()))
            throw new ExistingTransactionException("A Transaction exists with the same Transaction ID :  " + transaction.getId());

        if (transaction.getParent_id() != null && !this.transactionsMap.containsKey(transaction.getParent_id()))
            throw new IllegalTransactionParentIDException("Transaction Parent_Id does not correspond to a valid Transaction " + transaction.getParent_id());


        // add the transaction itself.
        this.transactionsMap.put(transaction.getId(), transaction);

        // add the transaction type. Convert all to LowerCase to avoid potential mistakes. Strings are Immutable, so preserve the reference to save memory.
        String transactionType = transaction.getType().toLowerCase();

        if (this.transactionsByTypeMap.containsKey(transactionType)) {
            this.transactionsByTypeMap.get(transactionType).add(transaction.getId());
        } else {
            List<Long> dummyList = new LinkedList<>();
            dummyList.add(transaction.getId());
            this.transactionsByTypeMap.put(transactionType, dummyList);
        }

        // add transaction parentId ..
        if (transaction.getParent_id() != null) {
            if (this.transactionsByParentIdMap.containsKey(transaction.getParent_id())) {
                this.transactionsByParentIdMap.get(transaction.getParent_id()).add(transaction.getId());
            } else {
                List<Long> dummyList = new LinkedList<>();
                dummyList.add(transaction.getId());
                this.transactionsByParentIdMap.put(transaction.getParent_id(), dummyList);
            }
        }

    }

    @Override
    public Transaction getTransaction(Long transactionId) throws NonExistingTransactionException {

        if (!this.transactionsMap.containsKey(transactionId))
            throw new NonExistingTransactionException("No Transaction exists with the given ID : " + transactionId);

        return this.transactionsMap.get(transactionId);
    }

    @Override
    public List<Long> getTransactionsByType(String type) throws NonExistingTransactionTypeException {

        String typeKey = type.toLowerCase();

        if (!this.transactionsByTypeMap.containsKey(typeKey))
            throw new NonExistingTransactionTypeException("No Transaction with the given type exists. Type is " + type);

        return this.transactionsByTypeMap.get(typeKey);
    }

    @Override
    public TransactionSum getTransactionsSumByParentId(Long transactionID) throws NonExistingTransactionException {

        if (!this.transactionsMap.containsKey(transactionID))
            throw new NonExistingTransactionException("No Transaction exists with the given ID :  " + transactionID);

        double sum = this.transactionsMap.get(transactionID).getAmount();

        List<Long> childrenTransactions = null;
        if (this.transactionsByParentIdMap.containsKey(transactionID)) {
            childrenTransactions = this.transactionsByParentIdMap.get(transactionID);
        }

        if (childrenTransactions != null) {
            for (Long id : childrenTransactions) {
                sum += this.transactionsMap.get(id).getAmount();
            }
        }

        return new TransactionSum(sum);
    }

    // For Testing Purposes.
    public Map<Long, Transaction> getTransactionsMap() {
        return transactionsMap;
    }
}
