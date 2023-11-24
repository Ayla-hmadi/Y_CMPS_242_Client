package com.example.yclient.Util;

import com.example.yclient.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Router {
    public void navigate(ActionEvent e, String path) throws IOException {
        Parent newRoot = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(path)));
        Node source = (Node) e.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.getScene().setRoot(newRoot);
    }
}
