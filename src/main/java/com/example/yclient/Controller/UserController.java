package com.example.yclient.Controller;

import com.example.yclient.Model.User;
import com.example.yclient.Service.BackendService;
import com.example.yclient.Util.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class UserController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label usernameLabel;
    private final Router router = new Router();

    @FXML
    private void follow(ActionEvent e) {
    }

    @FXML
    private void goToProfileView(ActionEvent e) throws IOException {
        router.navigate(e, "View/profile.fxml");
    }

    @FXML
    private void goToMainView(ActionEvent e) throws IOException {
        router.navigate(e, "View/feed.fxml");
    }

    @FXML
    private void goToExploreView(ActionEvent e) throws IOException {
        router.navigate(e, "View/explore.fxml");
    }

    @FXML
    private void initialize() {
        User user = BackendService.getLoginResponse().getUser();
        nameLabel.setText(user.getName());
        usernameLabel.setText("@" + user.getUsername());
    }
}