package ej05;

import java.io.*;
import java.net.*;

public class ClienteUDPInt {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();

            int numero = 3;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(numero);
            byte[] buffer = baos.toByteArray();

            InetAddress direccion = InetAddress.getByName("localhost");
            DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, direccion, 12345);

            socket.send(paquete);
            System.out.println("Entero enviado al servidor: " + numero);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}