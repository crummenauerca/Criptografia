package com.crummenauerca.cripto;
import javax.crypto.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

public class Bob {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        try {
            System.out.println("[Bob] Gerando par de chaves RSA...");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            ServerSocket serverSocket = new ServerSocket(5555);
            System.out.println("[Bob] Aguardando conexão na porta 5555");

            Socket socket = serverSocket.accept();
            System.out.println("[Bob] Conexão recebida. Oie, Alice :)");

            System.out.println("[Bob] Compartilhando chave pública");
            Object object = new Object();
            object.setPublicKey(publicKey);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(object);

            System.out.println("[Bob] Recebendo dados criptografafos");
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            object = (Object) objectInputStream.readObject();
            System.out.println("Dados criptografados:\n" + new String(object.getEncryptedFile()));

            Cipher cipherRSA = Cipher.getInstance("RSA");
            cipherRSA.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] plainText = cipherRSA.doFinal(object.getEncryptedFile());

            System.out.println("Dados descriptografados:\n" + new String(plainText));
        } catch(Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
