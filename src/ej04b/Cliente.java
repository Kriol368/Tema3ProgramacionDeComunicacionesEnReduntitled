package ej04b;

import java.io.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("Hola, servidor!");

            InputStream inputStream = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String respuesta = in.readLine();
            System.out.println(respuesta);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}