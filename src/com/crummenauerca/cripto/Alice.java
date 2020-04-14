package com.crummenauerca.cripto;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Alice {
    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, ClassNotFoundException {
        try {
            System.out.println("[Alice] Selecionando um arquivo");
            JFileChooser jFileChooser = new JFileChooser("");
            if (jFileChooser.showDialog(new JFrame(), "OK") == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] byteArray = new byte[(int) fileInputStream.getChannel().size()];
                fileInputStream.read(byteArray);

                System.out.println("[Alice] Conectando pela porta 5555");
                Socket socket = new Socket("localhost", 5555);

                System.out.println("[Alice] Conexão realizada. Oiee, Bob S2");
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                System.out.println("[Alice] Obtendo pacote com as chaves de criptografia");
                Object object = (Object) objectInputStream.readObject();

                System.out.println("[Alice] Descriptografando chave AES codificada");
                Cipher cipherRSA = Cipher.getInstance("RSA");
                cipherRSA.init(Cipher.DECRYPT_MODE, object.getPublicKey());
                byte[] encodedKey = cipherRSA.doFinal(object.getEncryptedKey());

                System.out.println("[Alice] Decodificando chave AES descriptografada");
                byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                SecretKey keyAES = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

                System.out.println("[Alice] Criptografando dados usando a chave AES recebida");
                Cipher cipherAES = Cipher.getInstance("AES");
                cipherAES.init(Cipher.ENCRYPT_MODE, keyAES);
                byte[] encryptedText = cipherAES.doFinal(byteArray);

                System.out.println("[Alice] Enviando dados criptografados");
                object.setEncryptedFile(encryptedText);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(object);

                System.out.println("[Alice] Encerrando a conexão. Adeus Bob :(");
                socket.close();
            } else {
                System.out.println("[Alice] Arquivo não selecionado!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
