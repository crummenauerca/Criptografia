package com.crummenauerca.cripto;

import javax.swing.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        System.out.println("Selecionando um arquivo");
        JFileChooser jFileChooser = new JFileChooser("");
        if (jFileChooser.showDialog(new JFrame(), "OK") == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] byteArrayFile = new byte[(int) fileInputStream.getChannel().size()];
            fileInputStream.read(byteArrayFile);
            System.out.println("Arquivo selecionado");

            System.out.println("Serializando pacote");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new ObjectOutputStream(byteArrayOutputStream).writeObject(byteArrayFile);
            byte[] byteArrayObject = byteArrayOutputStream.toByteArray();

            System.out.println("Gerando hash do arquivo selecionado");
            byte[] mac = MessageDigest.getInstance("SHA-256").digest(byteArrayObject);

            System.out.println("Hash do arquivo selecionado: " + mac.toString());
        }
    }
}
