/*
receptor/CRC-32.py


Laboratorio 02 - Redes de Computadores
Autores:
    - Melissa Pérez 21385
    - Fernanda Esquivel 21542
*/

import java.util.Scanner;
import java.util.zip.CRC32;
import java.util.Arrays;

public class CRC_32 {

    // Método para calcular el CRC-32 de un mensaje binario
    public static long calculateCRC32(String binaryMessage) {
        CRC32 crc = new CRC32();
        byte[] bytes = new byte[binaryMessage.length() / 8];

        // Convertir de binario a bytes
        for (int i = 0; i < binaryMessage.length(); i += 8) {
            int byteValue = Integer.parseInt(binaryMessage.substring(i, i + 8), 2);
            bytes[i / 8] = (byte) byteValue;
        }

        crc.update(bytes, 0, bytes.length);
        return crc.getValue();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar el mensaje completo
        System.out.print("Ingrese recibido en binario: ");
        String receivedMessage = scanner.nextLine();

        // Asumiendo que los últimos 32 bits son el CRC
        String originalMessage = receivedMessage.substring(0, receivedMessage.length() - 32);
        String receivedChecksumBinary = receivedMessage.substring(receivedMessage.length() - 32);

        // Calcular el CRC-32 del mensaje original
        long recalculatedCRC = calculateCRC32(originalMessage);
        String recalculatedCRCBinary = Long.toBinaryString(recalculatedCRC);

        // Asegurar que el CRC tenga 32 bits
        recalculatedCRCBinary = String.format("%32s", recalculatedCRCBinary).replace(' ', '0');

        // Comparar los CRC
        if (receivedChecksumBinary.equals(recalculatedCRCBinary)) {
            System.out.println("No se detectaron errores. Mensaje: " + originalMessage);
        } else {
            System.out.println("Se detectaron errores.");
        }

        scanner.close();
    }
}
