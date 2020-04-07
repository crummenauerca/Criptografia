package com.crummenauerca.cripto;

import javax.crypto.SecretKey;
import java.io.Serializable;

public class Object implements Serializable {
    private String fileName;
    private byte[] encryptedFile;
    private SecretKey key;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getEncryptedFile() {
        return encryptedFile;
    }

    public void setEncryptedFile(byte[] encryptedFile) {
        this.encryptedFile = encryptedFile;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }
}
