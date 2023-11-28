package com.example.yclient.Util;

import com.example.yclient.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Router {
    private static Stage currentStage;
    private static FXMLLoader currentLoader;

    public static FXMLLoader getCurrentLoader() {
        return currentLoader;
    }

    public static Stage getCurrentStage() {
        return currentStage;
    }

    public static Object getCurrentController() {
//        Scene currentScene = currentStage.getScene();
//        if (currentScene != null) {
//            FXMLLoader loader = (FXMLLoader) currentScene.getUserData();
//            if (loader != null) {
//                return loader.getController();
//            }
//        }
        if (currentLoader != null) {
            return currentLoader.getController();
        }
        return null;
    }

    public static void init(FXMLLoader loader, Stage stage) {
        currentLoader = loader;
        currentStage = stage;
    }

    public void navigate(ActionEvent e, String path) throws IOException {
        currentLoader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource(path)));
        Parent newRoot = currentLoader.load();
        Node source = (Node) e.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.getScene().setRoot(newRoot);
        currentStage = primaryStage;
    }

}
