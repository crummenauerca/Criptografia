package com.crummenauerca.cripto;

import java.io.Serializable;
import java.security.PublicKey;

public class Object implements Serializable {
    private byte[] encryptedFile;
    private PublicKey publicKey;

    public byte[] getEncryptedFile() {
        return encryptedFile;
    }

    public void setEncryptedFile(byte[] encryptedFile) {
        this.encryptedFile = encryptedFile;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
