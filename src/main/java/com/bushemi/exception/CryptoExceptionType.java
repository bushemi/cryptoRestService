package com.bushemi.exception;

public enum CryptoExceptionType {
    UNSUPPORTED_ENCODING("Unsupported encoding."),
    NO_SUCH_ALGORITHM("No such algorithm."),
    NO_SUCH_PADDING("No such padding"),
    INVALID_KEY("Invalid key"),
    ILLEGAL_BLOCK_SIZE("Illegal block size"),
    BAD_PADDING("Bad padding"),
    INVALID_ALGORITHM_PARAMETER("Invalid algorithm parameter");

    private String message;

    CryptoExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
