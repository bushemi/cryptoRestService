package com.bushemi.service;

import com.bushemi.exception.CryptoExceptionType;
import com.bushemi.exception.CryptoServiceException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

@Service
class AdvancedEncryptionStandardService implements EncryptionService {
    private static final String UTF_8 = "UTF-8";
    private static final String ALGORITHM = "AES";
    private static final int INIT_VECTOR_SIZE = 16;
    private static final String ALGORITHM_FULL_NAME = "AES/CBC/PKCS5PADDING";

    private byte[] key;
    private SecureRandom random;

     AdvancedEncryptionStandardService(@Value("${aes.key}") final String keyFromProperty) {
        try {
            this.key = keyFromProperty.getBytes(UTF_8);
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoServiceException(CryptoExceptionType.NO_SUCH_ALGORITHM);
        } catch (UnsupportedEncodingException e) {
            throw new CryptoServiceException(CryptoExceptionType.UNSUPPORTED_ENCODING);
        }
    }

    private byte[] generateInitVector() {
        byte[] initVector = new byte[INIT_VECTOR_SIZE];
        random.nextBytes(initVector);
        getUnsignedArrayOfBytes(initVector);
        return initVector;
    }

    private void getUnsignedArrayOfBytes(final byte[] initVector) {
        for (int i = 0; i < initVector.length; i++) {
            int unsignedOldByte = getUnsignedIntFromByte(initVector[i]);
            initVector[i] = (byte) unsignedOldByte;
        }
    }

    private int getUnsignedIntFromByte(final byte b) {
        int unsignedOldByte = Math.abs((int) b);
        if (unsignedOldByte < 0) {
            unsignedOldByte = Byte.MAX_VALUE;
        }
        return unsignedOldByte;
    }

    @Override
    public String encode(String text) {
        byte[] initVector = generateInitVector();
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);

            Cipher cipher = Cipher.getInstance(ALGORITHM_FULL_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

            byte[] encrypted = cipher.doFinal(text.getBytes());
            String codedString = Base64.encodeBase64String(encrypted);
            codedString = new String(initVector, UTF_8) + codedString;

            return codedString;
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoServiceException(CryptoExceptionType.NO_SUCH_ALGORITHM);
        } catch (NoSuchPaddingException e) {
            throw new CryptoServiceException(CryptoExceptionType.NO_SUCH_PADDING);
        } catch (InvalidKeyException e) {
            throw new CryptoServiceException(CryptoExceptionType.INVALID_KEY);
        } catch (IllegalBlockSizeException e) {
            throw new CryptoServiceException(CryptoExceptionType.ILLEGAL_BLOCK_SIZE);
        } catch (BadPaddingException e) {
            throw new CryptoServiceException(CryptoExceptionType.BAD_PADDING);
        } catch (InvalidAlgorithmParameterException e) {
            throw new CryptoServiceException(CryptoExceptionType.INVALID_ALGORITHM_PARAMETER);
        } catch (UnsupportedEncodingException e) {
            throw new CryptoServiceException(CryptoExceptionType.UNSUPPORTED_ENCODING);
        }
    }

    @Override
    public String decode(String text) {
        try {
            byte[] bytes = text.getBytes(UTF_8);
            byte[] initVector = Arrays.copyOf(bytes, INIT_VECTOR_SIZE);
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);

            Cipher cipher = Cipher.getInstance(ALGORITHM_FULL_NAME);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            text = text.substring(INIT_VECTOR_SIZE);
            byte[] original = cipher.doFinal(Base64.decodeBase64(text));

            return new String(original);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoServiceException(CryptoExceptionType.NO_SUCH_ALGORITHM);
        } catch (NoSuchPaddingException e) {
            throw new CryptoServiceException(CryptoExceptionType.NO_SUCH_PADDING);
        } catch (InvalidKeyException e) {
            throw new CryptoServiceException(CryptoExceptionType.INVALID_KEY);
        } catch (InvalidAlgorithmParameterException e) {
            throw new CryptoServiceException(CryptoExceptionType.INVALID_ALGORITHM_PARAMETER);
        } catch (IllegalBlockSizeException e) {
            throw new CryptoServiceException(CryptoExceptionType.ILLEGAL_BLOCK_SIZE);
        } catch (BadPaddingException e) {
            throw new CryptoServiceException(CryptoExceptionType.BAD_PADDING);
        } catch (UnsupportedEncodingException e) {
            throw new CryptoServiceException(CryptoExceptionType.UNSUPPORTED_ENCODING);
        }
    }
}


