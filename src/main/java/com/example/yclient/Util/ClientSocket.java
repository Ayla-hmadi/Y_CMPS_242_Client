package com.example.yclient.Util;

import java.io.*;
import java.net.Socket;

public class ClientSocket {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 6969;

    private static ClientSocket instance;
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader reader;

    private ClientSocket() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ClientSocket getInstance() {
        if (instance == null) {
            instance = new ClientSocket();
        }
        return instance;
    }

    public void send(String message) {
        printWriter.println(message);
    }

    public String receive() throws IOException{
        return reader.readLine();
    }

    public void close() throws IOException{
        printWriter.close();
        reader.close();
        socket.close();
    }
}
