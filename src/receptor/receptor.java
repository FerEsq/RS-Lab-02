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
            System.out.println("Receptor escuchando en puerto: " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String receivedMessage = in.readLine();

                    // Imprimir el mensaje recibido
                    System.out.println("\n>> Mensaje recibido del emisor: " + receivedMessage);

                    if (receivedMessage.length() > 32) {
                        // Procesar como mensaje CRC-32
                        boolean isValid = CRC_32.verifyCRC(receivedMessage);

                        if (isValid) {
                            System.out.println("(✓) Mensaje recibido correctamente con CRC-32: " + receivedMessage.substring(0, receivedMessage.length() - 32));
                        } else {
                            System.out.println("(✘) Se detectaron errores. Se descarta el mensaje.");
                        }
                    } else {
                        // Procesar como mensaje Hamming
                        String correctedMessage = Hamming.findAndCorrectErrors(receivedMessage);

                        if (correctedMessage == null) {
                            System.out.println("(✘) Error en la corrección de errores Hamming. Mensaje no válido.");
                        } else {
                            System.out.println("(✓) Mensaje recibido correctamente con Hamming: " + correctedMessage);
                        }
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