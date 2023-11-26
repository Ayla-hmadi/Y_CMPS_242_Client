package com.example.yclient.Controller;

import com.example.yclient.Main;
import com.example.yclient.Model.User;
import com.example.yclient.Service.BackendService;
import com.example.yclient.Util.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UsersController {
    @FXML
    private VBox usersContainer;
    @FXML
    private Label nameLabel;
    @FXML
    private Label usernameLabel;

    private final Router router = new Router();
    private final ArrayList<User> suggestedUsers = new ArrayList<>();

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
        for (int i = 0; i < 15; i++) {
            suggestedUsers.add(new User("username", "name" + i, "email@gmail.com", ""));
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
            followBtn.setOnAction(e->follow(u));
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

            usersContainer.getChildren().add(bp);
        }
    }
}
