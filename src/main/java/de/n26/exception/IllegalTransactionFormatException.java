package de.n26.exception;


public class IllegalTransactionFormatException extends Exception {

    public IllegalTransactionFormatException(String message){
        super(message);
    }
}
