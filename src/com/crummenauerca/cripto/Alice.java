package com.crummenauerca.cripto;

import javax.crypto.*;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Alice {
    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        JFileChooser jFileChooser = new JFileChooser("");
        System.out.println("Selecionando um arquivo");
        if (jFileChooser.showDialog(new JFrame(), "OK") == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] byteArray = new byte[(int) fileInputStream.getChannel().size()];
            fileInputStream.read(byteArray);
            System.out.println("Arquivo selecionado");

            Cipher cipherAES = Cipher.getInstance("AES");
            SecretKey keyAES = KeyGenerator.getInstance("AES").generateKey();
            cipherAES.init(Cipher.ENCRYPT_MODE, keyAES);
            byte[] encryptedText = cipherAES.doFinal(byteArray);

            Bob.sendFile(encryptedText, keyAES);
        } else {
            System.out.println("Arquivo não selecionado");
        }
    }
}
