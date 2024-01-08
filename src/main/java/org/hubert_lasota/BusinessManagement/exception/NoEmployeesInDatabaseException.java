package org.hubert_lasota.BusinessManagement.exception;

public class NoEmployeesInDatabaseException extends RuntimeException {

    public NoEmployeesInDatabaseException() {
        super("There are no employees in database!");
    }

    public NoEmployeesInDatabaseException(String message) {
        super(message);
    }

}
