package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {

    private ServerSocket serverSocket;
    private BufferedReader reader;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        createConnection(port);
        startListener();
    }

    private void readCommand() {
        String external_id;
        String message;
        Date time;
        String notification_type;
        String extra_params;
        try {
            external_id = reader.readLine();
            message = reader.readLine();
            long timeInMillis = Long.parseLong(reader.readLine());
            time = new Date(timeInMillis);
            notification_type = reader.readLine();
            extra_params = reader.readLine();
            Notification notification = new Notification(external_id, message, time, notification_type, extra_params);
            Task task = new Task(notification, time);
            task.run();
        }
        catch (IOException e) {
            System.err.println("Can't parse task, got message: " + e.getMessage());
        }
    }

    private void startListener() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    readCommand();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void createConnection(int port) {
        try {
            serverSocket = new ServerSocket(port, 10000);
            System.out.println("Server starts");
        } catch (IOException e) {
            System.err.println("Can't create connection");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(9999);
        server.start();
    }
}