package org.hubert_lasota.BusinessManagement.exception;

public class NoOrdersInDatabaseException extends RuntimeException {

    public NoOrdersInDatabaseException() {
        super("The are no orders in database!");
    }

    public NoOrdersInDatabaseException(String message) {
        super(message);
    }

}
