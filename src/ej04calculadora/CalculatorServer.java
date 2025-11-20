package ej04calculadora;

import java.io.*;
import java.net.*;

public class CalculatorServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor esperando conexiones en el puerto " + PORT + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar el socket del servidor al finalizar
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el servidor: " + e.getMessage());
                }
            }
        }
    }
}


class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientMessage;

            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Mensaje recibido del cliente: " + clientMessage);

                String result = processOperation(clientMessage);
                out.println(result);

                System.out.println("Resultado enviado al cliente: " + result);
            }

        } catch (IOException e) {
            System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
        } finally {
            closeResources();
        }
    }


    private String processOperation(String message) {
        try {
            String[] parts = message.split("#");

            if (parts.length != 3) {
                return "ERROR: Formato de mensaje incorrecto. Use: op#n1#n2";
            }

            int operation = Integer.parseInt(parts[0]);
            double num1 = Double.parseDouble(parts[1]);
            double num2 = Double.parseDouble(parts[2]);

            double result;
            switch (operation) {
                case 1: // Suma
                    result = num1 + num2;
                    break;
                case 2: // Resta
                    result = num1 - num2;
                    break;
                case 3: // Multiplicación
                    result = num1 * num2;
                    break;
                case 4: // División
                    if (num2 == 0) {
                        return "ERROR: No se puede dividir por cero";
                    }
                    result = num1 / num2;
                    break;
                default:
                    return "ERROR: Operación no válida. Use: 1=Suma, 2=Resta, 3=Multiplicación, 4=División";
            }

            if (result == (long) result) {
                return String.valueOf((long) result);
            } else {
                return String.valueOf(result);
            }

        } catch (NumberFormatException e) {
            return "ERROR: Los operandos deben ser números válidos";
        } catch (Exception e) {
            return "ERROR: Error procesando la operación: " + e.getMessage();
        }
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            System.out.println("Cliente desconectado: " + clientSocket.getInetAddress().getHostAddress());
        } catch (IOException e) {
            System.err.println("Error al cerrar recursos del cliente: " + e.getMessage());
        }
    }
}