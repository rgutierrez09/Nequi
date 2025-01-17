package com.nequi.db.exceptions;

import lombok.Getter;

@Getter
public class DBExceptions extends RuntimeException{
    private final DBErrorMessage errorMessage;
    public DBExceptions(DBErrorMessage dbErrorMessage) {
        super(dbErrorMessage.getMessage());
        this.errorMessage = dbErrorMessage;
    }
}
