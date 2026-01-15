package ej04b;

import java.net.*;

public class ServidorUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(12345);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRecibido);

                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                String clientIP = paqueteRecibido.getAddress().getHostAddress();
                int clientPort = paqueteRecibido.getPort();

                System.out.println("Mensaje del cliente: " + mensaje);

                String respuesta = "Cliente [" + clientIP + ":" + clientPort + "] conectado";
                byte[] datosRespuesta = respuesta.getBytes();
                DatagramPacket paqueteRespuesta = new DatagramPacket(
                        datosRespuesta,
                        datosRespuesta.length,
                        paqueteRecibido.getAddress(),
                        paqueteRecibido.getPort()
                );
                socket.send(paqueteRespuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}