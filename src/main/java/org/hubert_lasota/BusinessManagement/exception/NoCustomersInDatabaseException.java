package org.hubert_lasota.BusinessManagement.exception;

public class NoCustomersInDatabaseException extends Exception {

    public NoCustomersInDatabaseException() {
        super("There are no customers in database!");
    }
}
