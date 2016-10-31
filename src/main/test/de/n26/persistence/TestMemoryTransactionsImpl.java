package de.n26.persistence;


import de.n26.exception.ExistingTransactionException;
import de.n26.exception.IllegalTransactionParentIDException;
import de.n26.exception.NonExistingTransactionException;
import de.n26.exception.NonExistingTransactionTypeException;
import de.n26.model.Transaction;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestMemoryTransactionsImpl {

    private MemoryTransactionsImpl memoryTransactions;
    private Transaction transactionOne;
    private Transaction transactionTwo;
    private Transaction transactionThree;

    @Before
    public void setUp() {
        this.memoryTransactions = new MemoryTransactionsImpl();

        this.transactionOne = new Transaction();
        this.transactionOne.setId(1L);
        this.transactionOne.setAmount(20.0);
        this.transactionOne.setType("A");

        this.transactionTwo = new Transaction();
        this.transactionTwo.setId(2L);
        this.transactionTwo.setAmount(30.0);
        this.transactionTwo.setType("A");
        this.transactionTwo.setParent_id(transactionOne.getId());

        this.transactionThree = new Transaction();
        this.transactionThree.setId(3L);
        this.transactionThree.setAmount(50.0);
        this.transactionThree.setType("B");
        this.transactionThree.setParent_id(transactionOne.getId());
    }

    @Test(expected = ExistingTransactionException.class)
    public void testAddTransactionWithExistingTransaction() throws ExistingTransactionException, IllegalTransactionParentIDException {

        this.memoryTransactions.addTransaction(transactionOne);
        this.memoryTransactions.addTransaction(transactionOne);
    }

    @Test(expected = IllegalTransactionParentIDException.class)
    public void testAddTransactionWithNonExistingParentID() throws ExistingTransactionException, IllegalTransactionParentIDException {

        this.memoryTransactions.addTransaction(transactionTwo);
    }

    @Test
    public void testAddTransactionsWithValidInput() throws ExistingTransactionException, IllegalTransactionParentIDException {

        this.memoryTransactions.addTransaction(transactionOne);
        Assert.assertTrue("Adding Transaction is not working properly with valid input",
                this.memoryTransactions.getTransactionsMap().get(transactionOne.getId()).equals(transactionOne));

        this.memoryTransactions.addTransaction(transactionTwo);
        this.memoryTransactions.addTransaction(transactionThree);
        List<Transaction> values = Arrays.asList(new Transaction[]{transactionOne, transactionTwo, transactionThree});
        Assert.assertTrue("Adding Transaction is not working properly with valid input",
                this.memoryTransactions.getTransactionsMap().values().containsAll(values));
    }

    @Test(expected = NonExistingTransactionException.class)
    public void testGetTransactionWithNonExistingTransactionID() throws NonExistingTransactionException {

        this.memoryTransactions.getTransaction(transactionOne.getId());
    }

    @Test
    public void testGetTransactionWithValidInput() throws NonExistingTransactionException, ExistingTransactionException, IllegalTransactionParentIDException {

        this.memoryTransactions.addTransaction(transactionOne);
        this.memoryTransactions.addTransaction(transactionTwo);
        this.memoryTransactions.addTransaction(transactionThree);

        Assert.assertTrue("Get Transaction with Valid Input is not working properly with TransactionOne",
                this.memoryTransactions.getTransaction(transactionOne.getId()).equals(transactionOne));
        Assert.assertTrue("Get Transaction with Valid Input is not working properly with TransactionTwo",
                this.memoryTransactions.getTransaction(transactionTwo.getId()).equals(transactionTwo));
        Assert.assertTrue("Get Transaction with Valid Input is not working properly with TransactionThree",
                this.memoryTransactions.getTransaction(transactionThree.getId()).equals(transactionThree));
    }


    @Test(expected = NonExistingTransactionTypeException.class)
    public void testGetTransactionByTypeWithNonExistingType() throws NonExistingTransactionTypeException {

        this.memoryTransactions.getTransactionsByType(transactionOne.getType());
    }

    @Test
    public void testGetTransactionByTypeWithValidInputOfAnyCase() throws NonExistingTransactionTypeException, ExistingTransactionException, IllegalTransactionParentIDException {

        this.memoryTransactions.addTransaction(transactionOne);
        this.memoryTransactions.addTransaction(transactionTwo);
        this.memoryTransactions.addTransaction(transactionThree);

        List<Long> transactionListOfTypeA = this.memoryTransactions.getTransactionsByType("A");
        Assert.assertTrue("Get Transaction By Type is not working properly withValid Input Type 'A'",
                transactionListOfTypeA.containsAll(Arrays.asList(new Long[]{transactionOne.getId(), transactionTwo.getId()})));

        transactionListOfTypeA = this.memoryTransactions.getTransactionsByType("a");
        Assert.assertTrue("Get Transaction By Type is not working properly withValid Input Type 'A'",
                transactionListOfTypeA.containsAll(Arrays.asList(new Long[]{transactionOne.getId(), transactionTwo.getId()})));


        List<Long> transactionListOfTypeB = this.memoryTransactions.getTransactionsByType("B");
        Assert.assertTrue("Get Transaction By Type is not working properly with Valid Input Type 'B'",
                transactionListOfTypeB.containsAll(Arrays.asList(new Long[]{transactionThree.getId()})));


        transactionListOfTypeB = this.memoryTransactions.getTransactionsByType("b");
        Assert.assertTrue("Get Transaction By Type is not working properly with Valid Input Type 'B' ",
                transactionListOfTypeB.containsAll(Arrays.asList(new Long[]{transactionThree.getId()})));

    }

    @Test(expected = NonExistingTransactionException.class)
    public void testGetTransactionsSumByParentIdWithInvalidInput() throws NonExistingTransactionTypeException, NonExistingTransactionException {

        this.memoryTransactions.getTransactionsSumByParentId(transactionTwo.getParent_id());
    }

    @Test
    public void testGetTransactionsSumByParentIdWithValidInput() throws NonExistingTransactionTypeException, NonExistingTransactionException, ExistingTransactionException, IllegalTransactionParentIDException {

        this.memoryTransactions.addTransaction(transactionOne);
        this.memoryTransactions.addTransaction(transactionTwo);
        this.memoryTransactions.addTransaction(transactionThree);

        Double actualSum = this.memoryTransactions.getTransactionsSumByParentId(transactionTwo.getParent_id()).getSum();
        Double expectedSum = new Double(100.0);

        Assert.assertEquals("Get TransactionSum by Parent Id is not working properly", actualSum, expectedSum);
    }

}
