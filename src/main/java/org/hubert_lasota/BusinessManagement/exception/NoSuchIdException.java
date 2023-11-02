package org.hubert_lasota.BusinessManagement.exception;

public class NoSuchIdException extends RuntimeException {

    public NoSuchIdException() {
        super("There is no such id in database");
    }

}
