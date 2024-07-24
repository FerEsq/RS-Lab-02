"""
emisor/Hamming.py


Laboratorio 02 - Redes de Computadores
Autores:
    - Melissa Pérez 21385
    - Fernanda Esquivel 21542
"""

def requestBinaryMessage():
    # Solicitar al usuario que ingrese un mensaje binario
    while True:
        message = input("\033[37mIngrese el mensaje en binario: \033[37m")
        if all(c in '01' for c in message):
            return message
        else:
            print("\033[31mERROR: Mensaje no válido.\n\033[31m")

def calculateEvenParityBits(bits, m):
    n = 2**m - 1
    data = list(map(int, bits))
    data.reverse()
    parityPositions = [2**i for i in range(m)]
    
    #Insertar ceros en las posiciones de los bits de paridad
    for p in parityPositions:
        data.insert(p - 1, 0)
    
    #Calculo de los bits de paridad
    for p in parityPositions:
        start = p - 1
        i = start
        xor = 0
        
        while i < len(data):
            block = data[i:i + p]
            xor ^= sum(block)
            i += 2 * p
        
        data[start] = xor % 2  #Verificación de que el resultado es binario
    
    data.reverse()
    return ''.join(map(str, data))

def hammingCodeAlgorithm():
    #Solicitar el mensaje binario
    message = requestBinaryMessage()
    
    #Longitud del mensaje
    k = len(message)
    
    #Determinar m y n (basado en la longitud del mensaje)
    m = 0
    while (2**m - 1 - m) < k:
        m += 1
    n = 2**m - 1
    
    if k != n - m:
        print(f"\033[33m\nWARNING: El mensaje debe tener exactamente {n-m} bits, pero tiene {k}.\033[33m")
    
    #Calcular los bits de paridad (par) y formar el código de Hamming
    encodedMessage = calculateEvenParityBits(message, m)
    
    return encodedMessage

#Ejecutar el código de Hamming
hammingCode = hammingCodeAlgorithm()
print(f"\033[37m\n* El código de Hamming con paridad par es: \033[37m\033[32m{hammingCode}\033[0m")
