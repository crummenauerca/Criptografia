package com.crummenauerca.cripto;

import javax.crypto.*;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
                System.out.println("[Alice] Conexão realizada. Oi, Bob S2");
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                System.out.println("[Alice] Obtendo chave pública");
                Object object = (Object) objectInputStream.readObject();

                System.out.println("[Alice] Criptografando dados usando a chave pública");
                Cipher cipherRSA = Cipher.getInstance("RSA");
                cipherRSA.init(Cipher.ENCRYPT_MODE, object.getPublicKey());
                byte[] encryptedText = cipherRSA.doFinal(byteArray);

                System.out.println("[Alice] Enviando dados criptografados");
                object.setEncryptedFile(encryptedText);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(object);

                socket.close();
                System.out.println("[Alice] Conexão finalizada. Tchau Bob :(");
            } else {
                System.out.println("Arquivo não selecionado!");
            }
        } catch (Exception exception) {
            System.out.println("ERRO: " + exception.getMessage());
        }
    }
}
