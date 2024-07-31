"""
emisor/emisor.py


Laboratorio 02 pt.2 - Redes de Computadoras
Autores:
    - Melissa Pérez 21385
    - Fernanda Esquivel 21542
"""

import socket
import random
from CRC_32 import *
from Hamming import *

def prepare_message():
    """
    Application Layer:
    Requests a binary message after choosing the algorithm to check integrity.
    """
    choice = input("\nSeleccione el algoritmo de integridad: \n1. Hamming \n2. CRC-32\n>> ")
    message = requestBinaryMessage()

    if choice == '1':
        # Hamming
        message = encode_message(message)
    elif choice == '2':
        # CRC-32
        message = calculate_integrity(message)
    else:
        print("Opción no válida. Usando CRC-32 por defecto.")
        message = calculate_integrity(message)
    
    return message

def encode_message(message):
    """
    Presentation Layer:
    Encodes the message using Hamming for error correction.
    """
    encoded_message = hammingCodeAlgorithm(message)
    return encoded_message

def calculate_integrity(message):
    """
    Data Link Layer:
    Calculates the integrity information using CRC-32 and concatenates it to the message.
    """
    padded_message = message + '0' * ((8 - len(message) % 8) % 8)
    binary_message = int(padded_message, 2).to_bytes((len(padded_message) + 7) // 8, byteorder='big')
    crc_value = crc32(binary_message)
    crc_binary = hex_to_binary(f"{crc_value:08x}")
    final_message = padded_message + crc_binary
    return final_message

def apply_noise(message, probability):
    """
    Noise Layer:
    Applies random noise to the message based on the given probability.
    """
    noisy_message = ''
    for bit in message:
        if random.random() < probability:
            noisy_message += '1' if bit == '0' else '0'
        else:
            noisy_message += bit
    return noisy_message

def send_message(message, host, port, noise_probability):
    """
    Transmission Layer:
    Sends the information frame through sockets to the receiver.
    """
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((host, port))
        noisy_message = apply_noise(message, noise_probability)
        #print(f"Mensaje enviado: {noisy_message}")
        s.sendall(noisy_message.encode())

# Excecution
host = '127.0.0.1'
port = 8080
noise_probability = 0.01 # percentage of noise

binary_message = prepare_message()  # Request and prepare message
send_message(binary_message, host, port, noise_probability)  # Send message
