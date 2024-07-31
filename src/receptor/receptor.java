/*
receptor/receptor.java


Laboratorio 02 - Redes de Computadoras
Autores:
    - Melissa Pérez 21385
    - Fernanda Esquivel 21542
*/

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class receptor {
    public static void main(String[] args) {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor escuchando en puerto: " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String receivedMessage = in.readLine();

                    // Imprimir el mensaje recibido
                    System.out.println("Mensaje recibido del emisor: " + receivedMessage);

                    // Decodificación y corrección de errores
                    String correctedMessage = Hamming.findAndCorrectErrors(receivedMessage);

                    // Verificación del CRC
                    boolean isValid = CRC_32.verifyCRC(correctedMessage);

                    if (isValid) {
                        System.out.println("Mensaje recibido correctamente: " + correctedMessage);
                    } else {
                        System.out.println("Error en la verificación CRC. Mensaje corrupto.");
                    }
                } catch (IOException e) {
                    System.out.println("Error al recibir el mensaje: " + e.getMessage());
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}