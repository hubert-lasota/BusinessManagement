package org.hubert_lasota.BusinessManagement.exception;

public class NoAccountsInDatabaseException extends RuntimeException {

    public NoAccountsInDatabaseException() {
        super("There are no accounts in database!");
    }
}
