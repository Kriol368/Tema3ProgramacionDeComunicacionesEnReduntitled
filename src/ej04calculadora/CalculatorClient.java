package ej04calculadora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Cliente de calculadora que se conecta al servidor
 * para realizar operaciones matemáticas
 */
public class CalculatorClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Conectar al servidor
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Conexión establecida con el servidor.");

            boolean continueOperations = true;

            // Bucle principal para realizar múltiples operaciones
            while (continueOperations) {
                // Obtener los datos de la operación del usuario
                int operation = getOperationFromUser(scanner);
                double num1 = getNumberFromUser(scanner, "Introduce primer valor: ");
                double num2 = getNumberFromUser(scanner, "Introduce segundo valor: ");

                // Construir el mensaje con formato "op#n1#n2"
                String message = operation + "#" + num1 + "#" + num2;

                // Enviar la operación al servidor
                out.println(message);

                try {
                    // Recibir y mostrar el resultado del servidor
                    String result = in.readLine();
                    if (result != null) {
                        if (result.startsWith("ERROR")) {
                            System.out.println("Error del servidor: " + result);
                        } else {
                            System.out.println("El resultado es: " + result);
                        }
                    } else {
                        System.out.println("El servidor cerró la conexión.");
                        break;
                    }
                } catch (IOException e) {
                    System.err.println("Error al recibir respuesta del servidor: " + e.getMessage());
                    break;
                }

                // Preguntar si desea realizar otra operación
                continueOperations = askForAnotherOperation(scanner);
            }

            System.out.println("Cerrando conexión...");

        } catch (UnknownHostException e) {
            System.err.println("Error: No se pudo encontrar el servidor en " + SERVER_ADDRESS);
        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        } finally {
            // Cerrar recursos
            closeResources(socket, in, out, scanner);
        }
    }

    /**
     * Obtiene el tipo de operación del usuario
     *
     * @param scanner Scanner para leer la entrada
     * @return Código de operación válido
     */
    private static int getOperationFromUser(Scanner scanner) {
        int operation = 0;
        boolean validOperation = false;

        while (!validOperation) {
            try {
                System.out.print("Introduce tipo de operación: 1- Suma, 2- Resta, 3- Multiplicación, 4- División: ");
                operation = Integer.parseInt(scanner.nextLine());

                if (operation >= 1 && operation <= 4) {
                    validOperation = true;
                } else {
                    System.out.println("Error: La operación debe ser un número entre 1 y 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes introducir un número válido.");
            }
        }

        return operation;
    }

    /**
     * Obtiene un número del usuario
     *
     * @param scanner Scanner para leer la entrada
     * @param prompt  Mensaje a mostrar al usuario
     * @return Número válido introducido por el usuario
     */
    private static double getNumberFromUser(Scanner scanner, String prompt) {
        double number = 0;
        boolean validNumber = false;

        while (!validNumber) {
            try {
                System.out.print(prompt);
                number = Double.parseDouble(scanner.nextLine());
                validNumber = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes introducir un número válido.");
            }
        }

        return number;
    }

    /**
     * Pregunta al usuario si desea realizar otra operación
     *
     * @param scanner Scanner para leer la entrada
     * @return true si desea continuar, false si desea salir
     */
    private static boolean askForAnotherOperation(Scanner scanner) {
        int response = -1;
        boolean validResponse = false;

        while (!validResponse) {
            try {
                System.out.println("Si quieres hacer otra operación inserta cualquier valor numérico que no sea 0, si quieres salir inserta 0.");
                response = Integer.parseInt(scanner.nextLine());
                validResponse = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes introducir un número válido.");
            }
        }

        return response != 0;
    }

    /**
     * Cierra todos los recursos utilizados por el cliente
     */
    private static void closeResources(Socket socket, BufferedReader in, PrintWriter out, Scanner scanner) {
        try {
            if (scanner != null) scanner.close();
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}