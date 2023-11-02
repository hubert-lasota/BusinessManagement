package org.hubert_lasota.BusinessManagement.exception;

public class NoOrdersInDatabaseException extends  Exception {

    public NoOrdersInDatabaseException() {
        super("The are no orders in database!");
    }
}
