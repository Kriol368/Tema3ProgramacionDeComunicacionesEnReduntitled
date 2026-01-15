package ej05;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUDPInt {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(12345);
            System.out.println("Servidor UDP esperando enteros en el puerto 12345...");

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);

                socket.receive(paquete);

                ByteArrayInputStream bais = new ByteArrayInputStream(paquete.getData());
                DataInputStream dis = new DataInputStream(bais);
                int numeroRecibido = dis.readInt();

                String clientIP = paquete.getAddress().getHostAddress();
                int clientPort = paquete.getPort();

                System.out.println("Entero recibido del cliente [" + clientIP + ":" + clientPort + "]: " + numeroRecibido);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}