package ej05;

import java.io.*;
import java.net.*;

public class ServidorUDPBoolean {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(12346);
            System.out.println("Servidor UDP esperando booleanos en el puerto 12346...");

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);

                socket.receive(paquete);

                ByteArrayInputStream bais = new ByteArrayInputStream(paquete.getData());
                DataInputStream dis = new DataInputStream(bais);
                boolean valorRecibido = dis.readBoolean();

                String clientIP = paquete.getAddress().getHostAddress();
                int clientPort = paquete.getPort();

                System.out.println("Booleano recibido del cliente [" + clientIP + ":" + clientPort + "]: " + valorRecibido);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}