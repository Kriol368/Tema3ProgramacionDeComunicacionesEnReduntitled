package ej05;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClienteUDPBoolean {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();

            boolean valor = true;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeBoolean(valor);
            byte[] buffer = baos.toByteArray();

            InetAddress direccion = InetAddress.getByName("localhost");
            DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, direccion, 12346);

            socket.send(paquete);
            System.out.println("Booleano enviado al servidor: " + valor);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}