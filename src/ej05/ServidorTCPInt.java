package ej05;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCPInt {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor esperando conexiones en el puerto 12345...");

            while (true) {
                Socket clientSocket = serverSocket.accept();

                String clientIP = clientSocket.getInetAddress().getHostAddress();
                int clientPort = clientSocket.getPort();

                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                int numeroRecibido = in.readInt();
                System.out.println("Entero recibido del cliente [" + clientIP + ":" + clientPort + "]: " + numeroRecibido);

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}