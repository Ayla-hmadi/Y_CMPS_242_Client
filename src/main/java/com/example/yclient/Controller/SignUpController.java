package com.example.yclient.Controller;

import com.example.yclient.Service.BackendService;
import com.example.yclient.Util.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Label inputMessage;
    @FXML
    private Label inputMessage2;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
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
    private final Router router = new Router();

    @FXML
    protected void next() {
        if (nameField.getText().isBlank() || addressField.getText().isBlank() || emailField.getText().isBlank()) {
            inputMessage.setText("Please fill in all the required fields.");
            return;
        }
        inputMessage.setText("");
        infoInputBox1.setVisible(false);
        infoInputBox2.setVisible(true);
    }

    @FXML
    protected void prev() {
        infoInputBox2.setVisible(false);
        infoInputBox1.setVisible(true);
        inputMessage2.setText("");
    }

    @FXML
    protected void signUp(ActionEvent e) {
        try {
            if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
                inputMessage2.setText("Please enter your username and password.");
            } else {
                inputMessage2.setText("");
                var service = new BackendService();
                var loginResult = service.SignUp(
                        usernameField.getText(),
                        nameField.getText(),
                        emailField.getText(),
                        passwordField.getText(),
                        addressField.getText()
                );
                if (loginResult.isSuccess()) {
                    router.navigate(e, "View/feed.fxml");
                } else {
                    inputMessage2.setText("Oops! Something went wrong.");
                }
            }
        } catch (Exception ex) {
            inputMessage.setText("Oops! Something went wrong.");
            ex.printStackTrace();
        }
    }

    @FXML
    protected void goToSignIn(ActionEvent e) throws IOException {
        router.navigate(e, "View/main.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        infoInputBox2.setVisible(false);
    }
}
