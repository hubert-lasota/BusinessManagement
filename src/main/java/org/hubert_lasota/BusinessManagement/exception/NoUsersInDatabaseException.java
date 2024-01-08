package org.hubert_lasota.BusinessManagement.exception;

public class NoUsersInDatabaseException extends RuntimeException {

    public NoUsersInDatabaseException() {
        super("There are no users in database!");
    }

    public NoUsersInDatabaseException(String message) {
        super(message);
    }
}
