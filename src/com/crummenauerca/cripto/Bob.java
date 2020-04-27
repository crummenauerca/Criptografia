package com.crummenauerca.cripto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class Bob {
    public static void main(String[] args) {
        try {
            System.out.println("[Bob] Gerando par de chaves RSA para garantir confidencialidade...");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            System.out.println("[Bob] Empacotando chave RSA de Bob...");
            Object object = new Object();
            object.setPublicKey(keyPair.getPublic());

            System.out.println("[Bob] Aguardando conexão na porta 5555...");
            ServerSocket serverSocket = new ServerSocket(5555);
            Socket socket = serverSocket.accept();
            System.out.println("[Bob] Conexão recebida. Oie, Alice :)");

            System.out.println("[Bob] Enviando pacote de dados com a chave RSA pública para confidencialidade...");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(object);
            System.out.println("[Bob] Pacote recebido");

            System.out.println("[Bob] Recebendo pacote de dados...");
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            object = (Object) objectInputStream.readObject();

            System.out.println("[Bob] Conteúdo criptografado:\n" + new String(object.getEncryptedContent()) + "\n");

            System.out.println("[Bob] Descriptografando chave de sessão AES usando chave RSA privada de Bob...");
            Cipher cipherRSA = Cipher.getInstance("RSA");
            cipherRSA.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            SecretKey secretKey = new SecretKeySpec(cipherRSA.doFinal(object.getEncryptedSessionKey()), "AES");

            System.out.println("[Bob] Descriptografando conteúdo usando chave AES...");
            Cipher cipherAES = Cipher.getInstance("AES");
            cipherAES.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] content = cipherAES.doFinal(object.getEncryptedContent());

            System.out.println("[Bob] Conteúdo descriptografado:\n" + new String(content) + "\n");

            System.out.println("[Bob] Encerrando a conexão. Adeus Alice :(");
            socket.close();

            System.out.println("[Bob] Reconstituindo arquivo...");
            File file = new File("(Recebido) " + object.getFileName());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content);
            System.out.println("[Bob] Arquivo reconstruído");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}