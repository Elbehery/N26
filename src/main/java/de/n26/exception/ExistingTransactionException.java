package de.n26.exception;


public class ExistingTransactionException extends Exception{

    public ExistingTransactionException(String message) {
        super(message);
    }

}
