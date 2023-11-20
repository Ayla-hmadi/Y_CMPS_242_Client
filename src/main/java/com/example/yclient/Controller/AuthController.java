package com.example.yclient.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {
    @FXML
    private Label inputMessage;
    @FXML
    private Label inputMessage2;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private VBox infoInputBox1;
    @FXML
    private VBox infoInputBox2;

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
    protected void next(ActionEvent e){
        if(nameField.getText().isBlank() || addressField.getText().isBlank() || emailField.getText().isBlank()) {
            inputMessage.setText("Please fill in all the required fields.");
            return;
        }
        inputMessage.setText("");
        infoInputBox1.setVisible(false);
        infoInputBox2.setVisible(true);
    }

    @FXML
    protected void prev(ActionEvent e) {
        infoInputBox2.setVisible(false);
        infoInputBox1.setVisible(true);
    }

    @FXML
    protected void signUp(ActionEvent e) {
        try{
            if(usernameField.getText().isBlank() || passwordField.getText().isBlank()){
                inputMessage2.setText("Please enter your username and password.");
            } else {
                inputMessage2.setText("");
                System.out.println("signup");
            }
        } catch(Exception ex) {
            inputMessage2.setText("Oops! Something went wrong.");
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        infoInputBox2.setVisible(false);
    }
}
