package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Client.ClientHandler;

public class Main {
    private static final int PORT = 6379;

    public static void main(String[] args) {
        System.out.println("Server started...");

        ExecutorService executor = Executors.newFixedThreadPool(10); // Adjust pool size as needed

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setReuseAddress(true);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                executor.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
}
