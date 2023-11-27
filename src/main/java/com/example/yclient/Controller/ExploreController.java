package com.example.yclient.Controller;

import com.example.yclient.Main;
import com.example.yclient.Model.Post;
import com.example.yclient.Model.User;
import com.example.yclient.Service.BackendService;
import com.example.yclient.Util.ComponentBuilder;
import com.example.yclient.Util.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;

public class ExploreController {
    @FXML
    private VBox whoToFollow;
    @FXML
    private VBox whatsHappening;
    @FXML
    private Label nameLabel;
    @FXML
    private Label usernameLabel;
    private final Router router = new Router();
    private final ComponentBuilder cb = new ComponentBuilder();
    private final ArrayList<User> suggestedUsers = new ArrayList<>();
    private List<Post> suggestedPosts = new ArrayList<>();

    @FXML
    private void goToMainView(ActionEvent e) throws IOException {
        router.navigate(e, "View/feed.fxml");
    }

    @FXML
    private void goToProfileView(ActionEvent e) throws IOException {
        router.navigate(e, "View/profile.fxml");
    }

    private void follow(User user) {
        System.out.println("Follow: " + user);
    }

    @FXML
    public void initialize() {
        for (int i = 0; i < 3; i++) {
            suggestedUsers.add(new User("username", "name", "email@gmail.com", ""));
        }

        if (BackendService.getLoginResponse().getPosts() != null) {
            suggestedPosts = BackendService.getLoginResponse().getInterests();
            Collections.reverse(suggestedPosts);
        }

        User user = BackendService.getLoginResponse().getUser();
        nameLabel.setText(user.getName());
        usernameLabel.setText("@" + user.getUsername());

        for (User u : suggestedUsers) {
            BorderPane bp = new BorderPane();
            ImageView iv = new ImageView();
            iv.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("Asset/avatar.png"))));
            iv.setFitHeight(42);
            iv.setFitWidth(42);
            Button followBtn = new Button("Follow");
            followBtn.getStyleClass().add("btn-quaternary");
            followBtn.setOnAction(e -> follow(u));
            VBox vb = new VBox();
            Label nameLabel = new Label(u.getName());
            nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 700;");
            vb.getChildren().add(nameLabel);
            vb.getChildren().add(new Label("@" + u.getUsername()));
            vb.setStyle("-fx-padding: 0px 10px;");
            bp.setLeft(iv);
            bp.setCenter(vb);
            bp.setRight(followBtn);
            bp.getStyleClass().add("hover");
            bp.setStyle("-fx-padding: 8px 20px;");

            whoToFollow.getChildren().add(bp);
        }

        Button showMoreBtn = new Button();
        Label showMoreLabel = new Label("See more");
        showMoreBtn.setMaxWidth(Double.MAX_VALUE);
        showMoreBtn.setAlignment(Pos.CENTER);
        showMoreLabel.setStyle("-fx-text-fill: #33a6f2; -fx-padding: 10px; -fx-font-size: 14px; -fx-font-weight: 700;");
        showMoreBtn.setGraphic(showMoreLabel);
        showMoreBtn.setStyle("-fx-background-color: transparent;");
        showMoreBtn.setOnAction(e -> {
            try {
                router.navigate(e, "View/users.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        whoToFollow.getChildren().add(showMoreBtn);

        for (Post post : suggestedPosts) {
            whatsHappening.getChildren().add(cb.buildPost(post));
        }
    }
}
