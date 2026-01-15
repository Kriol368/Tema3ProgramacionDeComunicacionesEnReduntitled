package ej05;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCPBoolean {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor esperando conexiones en el puerto 12345...");

            while (true) {
                Socket clientSocket = serverSocket.accept();

                String clientIP = clientSocket.getInetAddress().getHostAddress();
                int clientPort = clientSocket.getPort();

                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                boolean valorRecibido = in.readBoolean();
                System.out.println("Booleano recibido del cliente [" + clientIP + ":" + clientPort + "]: " + valorRecibido);

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}