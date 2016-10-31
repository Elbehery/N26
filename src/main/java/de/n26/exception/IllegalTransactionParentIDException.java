package de.n26.exception;


public class IllegalTransactionParentIDException extends Exception {

    public IllegalTransactionParentIDException(String message) {
        super(message);
    }
}
