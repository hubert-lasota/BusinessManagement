package org.hubert_lasota.BusinessManagement.exception;

public class NoAddressesInDatabaseException extends RuntimeException {

    public NoAddressesInDatabaseException() {
        super("There are no addresses in database!");
    }

    public NoAddressesInDatabaseException(String message) {
        super(message);
    }

}
