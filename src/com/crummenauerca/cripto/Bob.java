package com.crummenauerca.cripto;
import javax.crypto.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.util.Base64;

public class Bob {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        try {
            System.out.println("[Bob] Gerando par de chaves RSA");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPairRSA = keyPairGenerator.generateKeyPair();

            System.out.println("[Bob] Gerando chave AES");
            SecretKey keyAES = KeyGenerator.getInstance("AES").generateKey();

            System.out.println("[Bob] Codificando chave AES");
            byte[] encodedKey = Base64.getEncoder().encode(keyAES.getEncoded());

            System.out.println("[Bob] Criptografando chave AES codificada usando a chave privada RSA");
            Cipher cipherRSA = Cipher.getInstance("RSA");
            cipherRSA.init(Cipher.ENCRYPT_MODE, keyPairRSA.getPrivate());
            byte[] encryptedKey = cipherRSA.doFinal(encodedKey);

            System.out.println("[Bob] Aguardando conexão na porta 5555");
            ServerSocket serverSocket = new ServerSocket(5555);

            Socket socket = serverSocket.accept();
            System.out.println("[Bob] Conexão recebida. Oie, Alice :)");

            Object object = new Object();
            System.out.println("[Bob] Empacotando chave pública RSA");
            object.setPublicKey(keyPairRSA.getPublic());
            System.out.println("[Bob] Empacotando chave AES (codificada e criptografada)");
            object.setEncryptedKey(encryptedKey);

            System.out.println("[Bob] Compartilhando pacote com chaves criptografadas");
            new ObjectOutputStream(socket.getOutputStream()).writeObject(object);

            System.out.println("[Bob] Recebendo dados criptografados");
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            object = (Object) objectInputStream.readObject();
            System.out.println("[Bob] Dados criptografados:\n" + new String(object.getEncryptedFile()));

            System.out.println("[Bob] Descriptografando dados recebidos usando AES");
            Cipher cipherAES = Cipher.getInstance("AES");
            cipherAES.init(Cipher.DECRYPT_MODE, keyAES);
            byte[] plainText = cipherAES.doFinal(object.getEncryptedFile());

            System.out.println("[Bob] Dados descriptografados:\n" + new String(plainText));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
