package com.crummenauerca.cripto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class Hellman {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(5000);
        Socket socket = serverSocket.accept();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        BigInteger[] QA = (BigInteger[]) objectInputStream.readObject();
        BigInteger YDiffie = (BigInteger) objectInputStream.readObject();

        BigInteger XHellman = Util.geraNumeroMenorQue(QA[0]);
        BigInteger YHellman = QA[1].modPow(XHellman, QA[0]);

        objectOutputStream.writeObject(YHellman);

        BigInteger k = YDiffie.modPow(XHellman, QA[0]);

        System.out.println("k = " + k);

        socket.close();
        serverSocket.close();
    }
}
