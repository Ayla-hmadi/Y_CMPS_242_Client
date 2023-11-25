package com.example.yclient.Controller;

import com.example.yclient.Model.Post;
import com.example.yclient.Util.ComponentBuilder;
import com.example.yclient.Util.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FeedController {
    @FXML
    private VBox postsContainer;
    private final Router router = new Router();
    private final ComponentBuilder cb = new ComponentBuilder();
    private final ArrayList<Post> posts = new ArrayList<>();

    @FXML
    private void handleTweet() {
    }

    @FXML
    public void initialize() {
        for (int i = 0; i < 10; i++) {
            posts.add(new Post(i, "Placeholder content number " + i, new Date(), "username" + i));
        }

        for (Post post : posts) {
            postsContainer.getChildren().add(cb.buildPost(post));
        }
    }

    @FXML
    private void goToProfileView(ActionEvent e) throws IOException {
        router.navigate(e, "View/profile.fxml");
    }

    @FXML
    private void goToExploreView(ActionEvent e) throws IOException {
        router.navigate(e, "View/explore.fxml");
    }
}