package com.bushemi.service;

public interface EncryptionService {

    /**
     * Encrypts the given plain text
     *
     * @param text The text to encrypt
     */
    String encode(String text);

    /**
     * Decrypts the given byte array
     *
     * @param text The data to decrypt
     */
    String decode(String text);
}
