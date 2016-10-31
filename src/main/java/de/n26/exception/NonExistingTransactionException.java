package de.n26.exception;


public class NonExistingTransactionException extends Exception {

    public NonExistingTransactionException(String message) {
        super(message);
    }


}
