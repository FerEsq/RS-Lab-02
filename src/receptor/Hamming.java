/*
receptor/Hamming.java


Laboratorio 02 - Redes de Computadores
Autores:
    - Melissa Pérez 21385
    - Fernanda Esquivel 21542
*/

import java.util.Scanner;

public class Hamming {
    // Método para encontrar y corregir errores, suponiendo que se llame desde otro lugar
    public static String findAndCorrectErrors(String received) {
        int m = calculateM(received.length());
        int n = (int) Math.pow(2, m) - 1;

        if (received.length() != n) {
            System.out.println("WARNING: El mensaje no coincide con la longitud esperada para cualquier combinación (n,m) válida.");
            return null; // Si la longitud no es válida, devolver null
        }

        int errorPosition = findError(received, m);
        if (errorPosition == 0) {
            System.out.println("No se detectaron errores.");
            return extractOriginalMessage(received, m);
        } else {
            System.out.println("Se detectaron errores. Corrigiendo...");
            received = correctError(received, errorPosition);
            return extractOriginalMessage(received, m);
        }
    }

    // Calcular m basado en la longitud del mensaje
    private static int calculateM(int length) {
        int m = 0;
        while ((int) Math.pow(2, m) - 1 < length) {
            m++;
        }
        return m;
    }

    // Encontrar la posición del error
    private static int findError(String received, int m) {
        int n = received.length();
        int errorPosition = 0;

        for (int i = 0; i < m; i++) {
            int parityCount = 0;
            int parityPosition = (1 << i);

            for (int j = 0; j < n; j++) {
                if ((j + 1 & parityPosition) != 0) {
                    if (received.charAt(n - 1 - j) == '1') {
                        parityCount++;
                    }
                }
            }

            if (parityCount % 2 != 0) {
                errorPosition += parityPosition;
            }
        }

        return errorPosition;
    }

    // Corregir el error en la posición dada
    private static String correctError(String received, int position) {
        int index = received.length() - position;
        char[] chars = received.toCharArray();
        chars[index] = chars[index] == '0' ? '1' : '0';
        return new String(chars);
    }

    // Extraer el mensaje original excluyendo los bits de paridad
    private static String extractOriginalMessage(String received, int m) {
        int n = received.length();
        StringBuilder originalMessage = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if ((i & (i - 1)) != 0) { // No es potencia de 2
                originalMessage.append(received.charAt(n - i));
            }
        }
        return originalMessage.reverse().toString();
    }
}