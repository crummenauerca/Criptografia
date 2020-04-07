package com.crummenauerca.cripto;

import javax.crypto.*;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
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

            Socket socket = new Socket("localhost", 5555);
            Object object = new Object();
            object.setEncryptedFile(encryptedText);
            object.setKey(keyAES);
            object.setFileName(file.getName());

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(object);

            socket.close();
        } else {
            System.out.println("Arquivo não selecionado");
        }
    }
}
