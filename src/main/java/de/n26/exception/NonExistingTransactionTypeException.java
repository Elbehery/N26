package de.n26.exception;

public class NonExistingTransactionTypeException extends Exception {

    public NonExistingTransactionTypeException(String message) {
        super(message);
    }


}
