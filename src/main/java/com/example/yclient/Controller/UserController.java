package com.example.yclient.Controller;

import com.example.yclient.Model.Post;
import com.example.yclient.Model.User;
import com.example.yclient.Service.BackendService;
import com.example.yclient.Util.ComponentBuilder;
import com.example.yclient.Util.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UserController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label userUsername;
    @FXML
    private Label userName;
    @FXML
    private Label userHandle;
    @FXML
    private VBox postsContainer;
    @FXML
    private Label followerCount;
    private final Router router = new Router();
    private final ComponentBuilder cb = new ComponentBuilder();

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

        userUsername.setText(BackendService.currentUserProfile.getUsername());
        userName.setText(BackendService.currentUserProfile.getName());
        userHandle.setText("@" + BackendService.currentUserProfile.getUsername());
        followerCount.setText(BackendService.currentUserProfile.getFollowing() + " Following     " + BackendService.currentUserProfile.getFollowers() + " Followers");

        for (Post post : BackendService.currentUserProfile.getPosts()) {
            postsContainer.getChildren().add(cb.buildPost(post));
        }
    }
}