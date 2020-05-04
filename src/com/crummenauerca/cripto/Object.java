package com.crummenauerca.cripto;

import java.io.Serializable;
import java.security.PublicKey;

public class Object implements Serializable {
    private String fileName;
    private byte[] signature;
    private byte[] encryptedContent;
    private byte[] encryptedSessionKey;
    private PublicKey publicKeyBob;
    private PublicKey publicKeyAlice;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getEncryptedContent() {
        return encryptedContent;
    }

    public void setEncryptedContent(byte[] encryptedContent) {
        this.encryptedContent = encryptedContent;
    }

    public byte[] getEncryptedSessionKey() {
        return encryptedSessionKey;
    }

    public void setEncryptedSessionKey(byte[] encryptedSessionKey) {
        this.encryptedSessionKey = encryptedSessionKey;
    }

    public PublicKey getPublicKeyBob() {
        return publicKeyBob;
    }

    public void setPublicKeyBob(PublicKey publicKeyBob) {
        this.publicKeyBob = publicKeyBob;
    }

    public PublicKey getPublicKeyAlice() {
        return publicKeyAlice;
    }

    public void setPublicKeyAlice(PublicKey publicKeyAlice) {
        this.publicKeyAlice = publicKeyAlice;
    }
}