package ej04calculadora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor de calculadora que maneja operaciones matemáticas
 * a través de sockets TCP/IP
 */
public class CalculatorServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Crear el socket del servidor
            System.out.println("Servidor esperando conexiones...");

            // Bucle principal para aceptar conexiones de clientes
            while (true) {
                // Esperar por una conexión de cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado");

                // Crear un hilo dedicado para manejar al cliente
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
        // Cerrar el socket del servidor al finalizar
    }
}

/**
 * Clase que maneja las solicitudes de un cliente específico en un hilo separado
 */
class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Configurar streams de entrada y salida
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientMessage;

            // Procesar mensajes del cliente hasta que se cierre la conexión
            while ((clientMessage = in.readLine()) != null) {
                // Procesar la operación y enviar resultado
                String result = processOperation(clientMessage);
                out.println(result);
            }

        } catch (IOException e) {
            System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
        } finally {
            // Cerrar recursos
            closeResources();
        }
    }

    /**
     * Procesa la operación matemática recibida del cliente
     *
     * @param message Mensaje con formato "op#n1#n2"
     * @return Resultado de la operación o mensaje de error
     */
    private String processOperation(String message) {
        try {
            // Dividir el mensaje usando el separador "#"
            String[] parts = message.split("#");

            // Validar que el mensaje tenga exactamente 3 partes
            if (parts.length != 3) {
                return "ERROR: Formato de mensaje incorrecto. Use: op#n1#n2";
            }

            // Obtener y validar los componentes de la operación
            int operation = Integer.parseInt(parts[0]);
            double num1 = Double.parseDouble(parts[1]);
            double num2 = Double.parseDouble(parts[2]);

            // Realizar la operación según el código recibido
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

            // Formatear el resultado (eliminar decimales si es entero)
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

    /**
     * Cierra los recursos de conexión con el cliente
     */
    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar recursos del cliente: " + e.getMessage());
        }
    }
}