package com.br.trabalho2carlos.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LojaSocket {
    private static final int PORT = 12345; // Porta do servidor
    private static final int THREAD_POOL_SIZE = 10; // Tamanho do pool de threads

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE); // Pool de threads

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);

            while (true) {
                System.out.println("Aguardando conexão de cliente...");
                Socket clientSocket = serverSocket.accept(); // Aceita conexões
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                // Cria uma thread para lidar com o cliente
                threadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }

    // Classe interna para lidar com o cliente
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    InputStream input = clientSocket.getInputStream();
                    OutputStream output = clientSocket.getOutputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    PrintWriter writer = new PrintWriter(output, true)) {
                writer.println("Bem-vindo à LojaSocket! Digite 'exit' para encerrar.");

                String message;
                while ((message = reader.readLine()) != null) {
                    if ("exit".equalsIgnoreCase(message)) {
                        writer.println("Conexão encerrada.");
                        break;
                    }

                    System.out.println("Mensagem recebida do cliente: " + message);
                    writer.println("Servidor recebeu: " + message);
                }
            } catch (IOException e) {
                System.err.println("Erro ao comunicar com o cliente: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar conexão do cliente: " + e.getMessage());
                }
            }
        }
    }
}
