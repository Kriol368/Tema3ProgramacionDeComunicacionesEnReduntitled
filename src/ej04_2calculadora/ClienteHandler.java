package ej04_2calculadora;

import java.io.*;
import java.net.Socket;

public class ClienteHandler implements Runnable {
    private final Socket clientSocket;

    public ClienteHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));

            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);

            String mensaje = in.readLine();
            System.out.println("Mensaje recibido del cliente: " + mensaje);

            out.println("Hola, cliente! Recibí tu mensaje");

            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}