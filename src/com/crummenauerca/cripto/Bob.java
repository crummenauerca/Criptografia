package com.crummenauerca.cripto;
import javax.crypto.*;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Bob {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5555);
            System.out.println("[Bob] Aguardando conexão na porta 5555.");
            Socket socket = serverSocket.accept();
            System.out.println("[Bob] Conexão recebida.");

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = (Object) objectInputStream.readObject();
            System.out.println("Texto cifrado:\n" + new String(object.getEncryptedFile()));

            Cipher cipherEAS = Cipher.getInstance("AES");
            cipherEAS.init(Cipher.DECRYPT_MODE, object.getKey());

            byte[] plainText = cipherEAS.doFinal(object.getEncryptedFile());

            System.out.println("Texto decifrado:\n" + new String(plainText));
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}
