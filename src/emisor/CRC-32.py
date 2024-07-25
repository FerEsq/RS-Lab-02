"""
emisor/CRC-32.py


Laboratorio 02 - Redes de Computadores
Autores:
    - Melissa Pérez 21385
    - Fernanda Esquivel 21542
"""

import binascii

def requestBinaryMessage():
    # Solicitar al usuario que ingrese un mensaje binario
    while True:
        message = input("\033[37mIngrese el mensaje en binario: \033[37m")
        if all(c in '01' for c in message):
            return message
        else:
            print("\033[31mERROR: Mensaje no válido.\n\033[31m")
            
def crc32(message):
    """
    Calculate the CRC-32 checksum of a given message.
    Args:
    message (bytes): The input message for which the CRC should be computed.
    
    Returns:
    int: The computed CRC-32 checksum.
    """
    # Convert the string message to bytes, if it's not already in bytes
    if isinstance(message, str):
        message = message.encode('utf-8')
    
    # Calculate CRC-32 using the binascii module
    crc_value = binascii.crc32(message) & 0xffffffff
    return crc_value

def hex_to_binary(hex_string):
    # Convertir el string hexadecimal a una cadena binaria
    binary_string = bin(int(hex_string, 16))[2:].zfill(32)
    return binary_string

def crc32Algorithm():
    # Solicitar el mensaje binario
    message = requestBinaryMessage()
    
    # Convertir el mensaje binario a bytes
    binary_message = int(message, 2).to_bytes((len(message) + 7) // 8, byteorder='big')
    
    # Calcular el checksum CRC-32
    checksum = crc32(binary_message)
    
    # Convertir el checksum a una cadena hexadecimal de 8 dígitos
    checksum_hex = f"{checksum:08x}"
    
    # Convertir el checksum hexadecimal a binario
    checksum_binary = hex_to_binary(checksum_hex)
    
    # Concatenar el checksum binario al mensaje original
    output_message = f"{message}{checksum_binary}"
    
    return output_message, checksum_binary

# Ejecutar el algoritmo CRC-32
encoded_message, checksum_binary = crc32Algorithm()
print(f"\033[37m\n* El mensaje con el CRC-32 (binario) es: \033[32m{encoded_message}\033[0m")
print(f"\033[37m* El checksum CRC-32 en binario es: \033[32m{checksum_binary}\033[0m")
