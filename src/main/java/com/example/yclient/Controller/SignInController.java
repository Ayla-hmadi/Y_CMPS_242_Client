package com.example.yclient.Controller;

import com.example.yclient.Util.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SignInController {
    @FXML
    private Label inputMessage;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    private final Router router = new Router();

    @FXML
    protected void signIn(ActionEvent e) {
        try {
            if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
                inputMessage.setText("Please enter your username and password.");
            } else {
                router.navigate(e, "View/feed.fxml");
                System.out.println("login");
            }
        } catch (Exception ex) {
            inputMessage.setText("Oops! Something went wrong.");
            ex.printStackTrace();
        }
    }

    @FXML
    protected void goToSignUp(ActionEvent e) throws IOException {
        router.navigate(e, "View/signup.fxml");
    }
}
