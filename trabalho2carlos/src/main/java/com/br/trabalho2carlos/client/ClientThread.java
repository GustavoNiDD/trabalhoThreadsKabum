package com.br.trabalho2carlos.client;

import java.io.*;
import java.net.*;

public class ClientThread {
    private static final String SERVER_ADDRESS = "localhost"; // Endereço do servidor
    private static final int SERVER_PORT = 12345; // Porta do servidor

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                PrintWriter writer = new PrintWriter(output, true);
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado ao servidor!");

            // Thread para ouvir mensagens do servidor
            Thread listenerThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = reader.readLine()) != null) {
                        System.out.println("Servidor: " + serverMessage);
                    }
                } catch (IOException e) {
                    System.err.println("Conexão encerrada pelo servidor.");
                }
            });

            listenerThread.start();

            // Enviar mensagens para o servidor
            String userMessage;
            while ((userMessage = consoleReader.readLine()) != null) {
                writer.println(userMessage);
                if ("exit".equalsIgnoreCase(userMessage)) {
                    break;
                }
            }

            listenerThread.join(); // Espera a thread de ouvinte terminar
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
        }
    }
}
