package com.example.yclient;

import com.example.yclient.Util.ClientSocket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static final String DEFAULT_SERVER_IP = "127.0.0.1";
    private static final int DEFAULT_SERVER_PORT = 43211;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Main.class.getResource("View/main.css").toExternalForm());

        stage.setTitle("Y. It's what's happening.");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if (args.length > 1) {
            ClientSocket.SERVER_IP = args[0];
            ClientSocket.SERVER_PORT = Integer.parseInt(args[1]);
        } else {
            ClientSocket.SERVER_PORT = DEFAULT_SERVER_PORT;
            ClientSocket.SERVER_IP = DEFAULT_SERVER_IP;
        }

        launch();
    }

    @Override
    public void stop() throws Exception {
        ClientSocket.terminate();
        super.stop();
    }
}