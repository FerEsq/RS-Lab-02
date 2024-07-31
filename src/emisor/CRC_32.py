"""
emisor/CRC-32.py


Laboratorio 02 - Redes de Computadoras
Autores:
    - Melissa Pérez 21385
    - Fernanda Esquivel 21542
"""

import binascii

def requestBinaryMessage():
    """
    Request the user to input a binary message.
    Returns:
    str: The binary message input by the user.
    """
    # Solicitar al usuario que ingrese un mensaje binario
    while True:
        message = input("\n\033[37mIngrese el mensaje en binario: \033[37m")
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
    """
    Convert a hexadecimal string to a binary string.
    Args:
    hex_string (str): The hexadecimal string to convert.
    
    Returns:
    str: The binary string representation of the hexadecimal string.
    """
    # Convertir el string hexadecimal a una cadena binaria
    binary_string = bin(int(hex_string, 16))[2:].zfill(32)
    return binary_string

def crc32Algorithm():
    """
    Implement the CRC-32 algorithm to encode a binary message.
    Returns:
    str: The encoded message with the CRC-32 checksum.
    """
    # Solicitar el mensaje binario
    message = requestBinaryMessage()
    
    # Añadir ceros al final del mensaje original para hacer la longitud múltiplo de 8
    padded_message = message + '0' * ((8 - len(message) % 8) % 8)
    
    # Convertir el mensaje binario a bytes
    binary_message = int(padded_message, 2).to_bytes((len(padded_message) + 7) // 8, byteorder='big')
    
    # Calcular el checksum CRC-32
    checksum = crc32(binary_message)
    
    # Convertir el checksum a una cadena hexadecimal de 8 dígitos
    checksum_hex = f"{checksum:08x}"
    
    # Convertir el checksum hexadecimal a binario
    checksum_binary = hex_to_binary(checksum_hex)
    
    # Concatenar el checksum binario al mensaje original ya rellenado
    output_message = f"{padded_message}{checksum_binary}"
    
    return output_message, checksum_binary

"""
# Ejecutar el algoritmo CRC-32
encoded_message, checksum_binary = crc32Algorithm()
print(f"\033[37m\n* El mensaje con el CRC-32 (binario) es: \033[32m{encoded_message}\033[0m")
"""