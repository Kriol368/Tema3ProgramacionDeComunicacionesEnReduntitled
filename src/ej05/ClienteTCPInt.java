package ej05;

import java.io.*;
import java.net.Socket;

public class ClienteTCPInt {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            int numero = 3;
            out.writeInt(numero);
            System.out.println("Enviado entero al servidor: " + numero);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}