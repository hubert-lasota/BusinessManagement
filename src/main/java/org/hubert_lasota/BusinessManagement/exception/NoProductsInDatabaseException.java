package org.hubert_lasota.BusinessManagement.exception;

public class NoProductsInDatabaseException extends RuntimeException {

    public NoProductsInDatabaseException() {
        super("There are no products in database!");
    }
}
