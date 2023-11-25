package com.example.yclient.Controller;

import com.example.yclient.Util.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class UserController {
    private final Router router = new Router();

    @FXML
    private void follow(ActionEvent e) {
    }

    @FXML
    private void goToProfileView(ActionEvent e) throws IOException {
        router.navigate(e, "View/user.fxml");
    }

    @FXML
    private void goToMainView(ActionEvent e) throws IOException {
        router.navigate(e, "View/feed.fxml");
    }

    @FXML
    private void goToExploreView(ActionEvent e) throws IOException {
        router.navigate(e, "View/explore.fxml");
    }
}