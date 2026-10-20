package org.example.db;

public class DBIntegrityException extends RuntimeException {
    public DBIntegrityException(String msg) {
        super(msg);
    }
}
