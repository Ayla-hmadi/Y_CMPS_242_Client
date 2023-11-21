package com.example.yclient.Controller;

import com.example.yclient.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignInController {
    @FXML
    private Label inputMessage;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void signIn() {
        try {
            if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
                inputMessage.setText("Please enter your username and password.");
            } else {
                System.out.println("login");
            }
        } catch (Exception ex) {
            inputMessage.setText("Oops! Something went wrong.");
            ex.printStackTrace();
        }
    }

    @FXML
    protected void goToSignUp(ActionEvent e) throws IOException {
        Parent newRoot = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("View/signup.fxml")));
        Node source = (Node) e.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.getScene().setRoot(newRoot);
    }
}
