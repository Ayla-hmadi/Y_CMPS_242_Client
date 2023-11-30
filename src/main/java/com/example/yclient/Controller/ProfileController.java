package com.example.yclient.Controller;

import com.example.yclient.Model.Following;
import com.example.yclient.Model.Post;
import com.example.yclient.Model.User;
import com.example.yclient.Service.BackendService;
import com.example.yclient.Util.ComponentBuilder;
import com.example.yclient.Util.NetworkManager;
import com.example.yclient.Util.Router;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ProfileController {
    @FXML
    private VBox postsContainer;
    @FXML
    private Label appBarLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label profileNameLabel;
    @FXML
    private Label profileUsernameLabel;
    @FXML
    private Label followerCount;

    private final Router router = new Router();
    private final ComponentBuilder cb = new ComponentBuilder();

    @FXML
    private void logout(ActionEvent e) throws IOException{
        NetworkManager.terminate();
        router.navigate(e, "View/main.fxml");
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
    public void initialize() {
        List<Post> posts = BackendService.getLoginResponse().getPosts();
        Collections.reverse(posts);

        var service = new BackendService();
        var following = service.getFollowing().size();
        var followers = service.getFollowers().size();

        User user = BackendService.getLoginResponse().getUser();

        appBarLabel.setText(user.getName());
        nameLabel.setText(user.getName());
        usernameLabel.setText("@" + user.getUsername());
        profileNameLabel.setText(user.getName());
        profileUsernameLabel.setText("@" + user.getUsername());

        for (Post post : posts) {
            postsContainer.getChildren().add(cb.buildPost(post));
        }

        followerCount.setText(following + " Following     " + followers + " Followers");
    }
}