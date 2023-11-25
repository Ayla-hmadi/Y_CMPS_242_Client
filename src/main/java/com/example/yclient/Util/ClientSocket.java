package com.example.yclient.Util;

import java.io.*;
import java.net.Socket;

public class ClientSocket {
    /**
     * Kills the instance
     */
    public static void terminate() throws IOException {
        if (instance != null) {
            instance.close();
        }
    }

    /**
     * singleton
     *
     * @return
     */
    public static ClientSocket getInstance() {
        if (instance == null) {
            instance = new ClientSocket();
        }
        return instance;
    }

    public static String SERVER_IP = "127.0.0.1";
    public static int SERVER_PORT = 6969;

    private static ClientSocket instance;
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader reader;

    private ClientSocket() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!reader.readLine().equals("OVER"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        printWriter.println(message);
    }

    public String receive() throws IOException {
        return reader.readLine();
    }

    public String tryReceive() {
        try {
            return receive();
        } catch (Exception ignored) {
        }
        return null;
    }

    public void close() throws IOException {
        printWriter.close();
        reader.close();
        socket.close();
    }
}
