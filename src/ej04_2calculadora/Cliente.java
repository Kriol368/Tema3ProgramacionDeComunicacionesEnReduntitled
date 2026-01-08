package ej04_2calculadora;

import java.io.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Conexión establecida con el servidor");

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);

            out.println("Hola, servidor!");

            InputStream input = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            String respuesta = in.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}