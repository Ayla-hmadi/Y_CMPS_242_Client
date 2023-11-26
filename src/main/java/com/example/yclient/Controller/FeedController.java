package com.example.yclient.Controller;

import com.example.yclient.Model.Post;
import com.example.yclient.Model.User;
import com.example.yclient.Service.BackendService;
import com.example.yclient.Util.ComponentBuilder;
import com.example.yclient.Util.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedController {
    @FXML
    private VBox postsContainer;
    @FXML
    private TextArea postTextArea;
    @FXML
    private Label nameLabel;
    @FXML
    private Label usernameLabel;
    private final Router router = new Router();
    private final ComponentBuilder cb = new ComponentBuilder();
    private final List<Post> posts = new ArrayList<>();

    @FXML
    private void post() {
        if (postTextArea.getText().isBlank()) {
            return;
        }

        System.out.println(postTextArea.getText());
    }

    @FXML
    public void initialize() {
        for (int i = 0; i < 10; i++) {
            posts.add(new Post(i, "Placeholder content number " + i, new Date(), "username" + i));
        }

        User user = BackendService.getLoginResponse().getUser();
        nameLabel.setText(user.getName());
        usernameLabel.setText("@" + user.getUsername());

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