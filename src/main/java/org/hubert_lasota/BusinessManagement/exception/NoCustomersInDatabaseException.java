package org.hubert_lasota.BusinessManagement.exception;

public class NoCustomersInDatabaseException extends RuntimeException {

    public NoCustomersInDatabaseException() {
        super("There are no customers in database!");
    }

    public NoCustomersInDatabaseException(String message) {
        super(message);
    }
}
