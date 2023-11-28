package com.example.yclient.Util;

import com.example.yclient.Controller.FeedController;
import com.example.yclient.Model.Post;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class MultiThreadClientSocket {
    public static final String EventPrefix = "event:";
    public static String SERVER_IP_ADDRESS = "127.0.0.1";
    public static int SERVER_PORT = 6969;
    //    private final InputStreamReader inputStream;
    private PipedOutputStream pipedOutputStream;
    private PipedInputStream pipedInputStream;
    //    private BufferedInputStream sharedInputStream;
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader reader;
    private Thread readerThread;

    MultiThreadClientSocket() {
        try {
            socket = new Socket(SERVER_IP_ADDRESS, SERVER_PORT);
//            sharedInputStream = new BufferedInputStream(socket.getInputStream());
//            inputStream = new InputStreamReader(socket.getInputStream());
            pipedOutputStream = new PipedOutputStream();
            pipedInputStream = new PipedInputStream(pipedOutputStream);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(pipedInputStream));
//            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Consider removing the while loop or clarify its purpose
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception or handle it more gracefully
        }

        startReaderThread();
    }

    public void startReaderThread() {
        readerThread = new Thread(() -> {
            try (
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(pipedOutputStream), true);
                    BufferedReader sharedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {
                while (!sharedReader.readLine().equals("OVER")) ;
                String serverResponse;
                while ((serverResponse = sharedReader.readLine()) != null) {
                    if (serverResponse.startsWith(EventPrefix)) {
                        System.out.println("[server event]: " + serverResponse);
                        var json = serverResponse.substring(EventPrefix.length());
                        try {
                            var post = new Gson().fromJson(json, Post.class);
                            Platform.runLater(() -> {
                                // show notification
                                NotificationUtil.showNotification("[New post] " + post.getUsername() + ": " + post.getContent());
                                // update posts
                                var controller = Router.getCurrentController();
                                if (controller instanceof FeedController) {
                                    var feedController = (FeedController) controller;
                                    feedController.refreshFeed();
                                }
                            });
                        } catch (JsonSyntaxException e) {
                            System.out.println("Invalid json");
                        }
                    } else {
                        System.out.println("Received from server: " + serverResponse);
                        writer.println(serverResponse);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); // Log the exception or handle it more gracefully
            }
        });
        readerThread.start();
//        try {
//            readerThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void send(String message) {
        printWriter.println(message);
    }

//    public String receive() throws IOException {
//        return reader.readLine();
//    }

    public String tryReceive() {
        try {
            return reader.readLine();
        } catch (IOException ignored) {
        }
        return null;
    }

    public void close() {
//        try {
//            readerThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        try {
            if (pipedInputStream != null) {
                pipedInputStream.close();
            }
            if (pipedOutputStream != null) {
                pipedOutputStream.close();
            }
            if (printWriter != null) {
                printWriter.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception or handle it more gracefully
        }
    }
}
