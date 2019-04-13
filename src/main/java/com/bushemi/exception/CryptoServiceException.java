package com.bushemi.exception;

public class CryptoServiceException extends RuntimeException {
    private final String message;

    public CryptoServiceException(CryptoExceptionType cryptoExceptionType) {
        this.message = cryptoExceptionType.getMessage();
    }

    @Override
    public String toString() {
        return "CryptoServiceException{" +
                "message='" + message + '\'' +
                "} ";
    }

}
