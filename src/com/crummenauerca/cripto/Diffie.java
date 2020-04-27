package com.crummenauerca.cripto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;

public class Diffie {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 5000);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        BigInteger[] QA = Util.geraQA(128);
        objectOutputStream.writeObject(QA);
        BigInteger XDiffie = Util.geraNumeroMenorQue(QA[0]);
        BigInteger YDiffie = QA[1].modPow(XDiffie, QA[0]);

        objectOutputStream.writeObject(YDiffie);

        BigInteger YHellman = (BigInteger) objectInputStream.readObject();
        BigInteger k = YHellman.modPow(XDiffie, QA[0]);

        System.out.println("k = " + k);

        socket.close();
    }
}
