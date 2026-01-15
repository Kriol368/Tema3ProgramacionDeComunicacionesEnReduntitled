package ej05;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClienteTCPBoolean {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            boolean valor = true;
            out.writeBoolean(valor);
            System.out.println("Enviado booleano al servidor: " + valor);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}