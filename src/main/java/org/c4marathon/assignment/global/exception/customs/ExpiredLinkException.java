package org.c4marathon.assignment.global.exception.customs;

public class ExpiredLinkException extends RuntimeException {

    public ExpiredLinkException(String message) {
        super(message);
    }

    public ExpiredLinkException(String message, Throwable cause) {
        super(message, cause);
    }

}
