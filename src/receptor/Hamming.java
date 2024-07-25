/*
receptor/Hamming.java


Laboratorio 02 - Redes de Computadores
Autores:
    - Melissa Pérez 21385
    - Fernanda Esquivel 21542
*/

import java.util.Scanner;

public class Hamming {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el mensaje recibido en binario: ");
        String received = scanner.next();
        int m = calculateM(received.length());
        int n = (int) Math.pow(2, m) - 1;

        if (received.length() != n) {
            System.out.println("WARNING: El mensaje no coincide con la longitud esperada para cualquier combinación (n,m) válida.");
        }

        if (received.length() < 3) {
            System.out.println("ERROR: Mensaje no válido.");
            return;
        }
        
        int errorPosition = findError(received, m);
        if (errorPosition == 0) {
            System.out.println("\n[v] No se detectaron errores.");
            System.out.println("* Mensaje original: " + extractOriginalMessage(received, m));
        } else {
            System.out.println("\n[x] Se detectaron errores.");
            received = correctError(received, errorPosition);
            System.out.println("* Mensaje corregido: " + extractOriginalMessage(received, m));
        }
    }

    private static int calculateM(int length) {
        int m = 0;
        while ((int) Math.pow(2, m) - 1 < length) {
            m++;
        }
        return m;
    }

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

    private static String correctError(String received, int position) {
        int index = received.length() - position;
        char[] chars = received.toCharArray();
        chars[index] = chars[index] == '0' ? '1' : '0';  //Corregir el bit en la posición calculada
        return new String(chars);
    }

    private static String extractOriginalMessage(String received, int m) {
        int n = received.length();
        StringBuilder originalMessage = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if ((i & (i - 1)) != 0) { //Verificar si no es potencia de 2
                originalMessage.append(received.charAt(n - i));
            }
        }
        return originalMessage.reverse().toString();
    }
}