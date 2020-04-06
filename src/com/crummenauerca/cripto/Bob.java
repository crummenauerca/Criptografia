package com.crummenauerca.cripto;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Bob {
    public static void sendFile(byte[] encryptedText, SecretKey keyAES) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        System.out.println("Texto cifrado:\n" + new String(encryptedText));
        byte[] plainText = null;
        Cipher cipherAES = Cipher.getInstance("AES");
        cipherAES.init(Cipher.DECRYPT_MODE, keyAES);
        plainText = cipherAES.doFinal(encryptedText);
        System.out.println("Texto decifrado:\n" + new String(plainText));
    }
}
