package com.example.yclient.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AuthController {
    @FXML
    private Label inputMessage;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    @FXML
    protected void signIn(ActionEvent e) {
        try{
            if(usernameField.getText().isBlank() || passwordField.getText().isBlank()){
                inputMessage.setText("Please enter your username and password.");
            } else {
                System.out.println("login");
            }
        } catch(Exception ex) {
            inputMessage.setText("Oops! Something went wrong.");
            ex.printStackTrace();
        }
    }

    @FXML
    protected void createAccount(ActionEvent e) {

    }

    @FXML
    protected void signUp(ActionEvent e) {
        System.out.println("sign up");
    }
}
