package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class Server {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private BufferedReader reader;
    private static final int PORT = 5959;
    private static final int MAX_CONNECTION = 1000;

    private void start() {
        createConnection();
        serverListener();
    }

    private void readCommand() {
        try {
            String subject = reader.readLine();
            String message = reader.readLine();
            String email = reader.readLine();
            int year =  Integer.parseInt(reader.readLine());
            int month =  Integer.parseInt(reader.readLine());
            int day =  Integer.parseInt(reader.readLine());
            Date date = new Date(year - 1900, month, day);

            Notification notification = new Notification(subject, message, email, date);
            Task task = new Task(notification, date);
            task.run();
        }
        catch (IOException e) {
            System.err.println("Can't parse task, got message: " + e.getMessage());
        }
    }

    private void serverListener() {
        new Thread(() -> {
            while (true) {
                try {
                    try {
                        socket = serverSocket.accept();
                        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        readCommand();
                    }
                    finally {
                        socket.close();
                        reader.close();
                    }
                }
                 catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void createConnection() {
        try {
            serverSocket = new ServerSocket(PORT, MAX_CONNECTION);
            System.out.println("Server is started!");
        } catch (IOException e) {
            System.err.println("Can't create connection");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}