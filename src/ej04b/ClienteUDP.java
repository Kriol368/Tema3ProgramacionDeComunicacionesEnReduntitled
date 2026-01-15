package ej04b;

import java.net.*;

public class ClienteUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();

            String mensaje = "Hola, servidor!";
            byte[] datosEnvio = mensaje.getBytes();
            InetAddress direccion = InetAddress.getByName("localhost");
            DatagramPacket paqueteEnvio = new DatagramPacket(
                    datosEnvio,
                    datosEnvio.length,
                    direccion,
                    12345
            );
            socket.send(paqueteEnvio);

            byte[] buffer = new byte[1024];
            DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
            socket.receive(paqueteRecibido);

            String respuesta = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
            System.out.println(respuesta);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}