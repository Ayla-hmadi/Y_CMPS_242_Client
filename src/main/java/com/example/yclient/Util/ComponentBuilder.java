package com.example.yclient.Util;

import com.example.yclient.Main;
import com.example.yclient.Model.Post;
import com.example.yclient.Model.enums.ReactionType;
import com.example.yclient.Service.BackendService;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ComponentBuilder {
    private final Router router = new Router();

    private final Map<Integer, Map<ReactionType, Button>> postReactions = new HashMap<>();

    private Button getIconButton(String iconPath, ReactionType type, int postId) {
        Button btn = new Button();
        ImageView iv = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream(iconPath))));
        iv.setFitHeight(18);
        iv.setFitWidth(18);
        btn.setGraphic(iv);
        btn.getStyleClass().add("btn-icon");
        btn.setOnAction(e -> react(postId, type, btn));
        postReactions.computeIfAbsent(postId, k -> new HashMap<>()).put(type, btn);
        return btn;
    }

    public BorderPane buildPost(Post post) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        BorderPane bp = new BorderPane();
        ImageView iv = new ImageView();
        iv.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("Asset/avatar.png"))));
        iv.setFitHeight(42);
        iv.setFitWidth(42);
        VBox vb = new VBox();
        HBox hb = new HBox();
        hb.setSpacing(4);
        Button nameBtn = new Button();
        Label nameLabel = new Label(post.getUsername());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 700;");
        nameBtn.setGraphic(nameLabel);
        nameBtn.setOnAction(e -> {
            try {
                new BackendService().getUserInfo(post.getUsername());
                if (BackendService.loginResponse.getUser().getUsername().equals(post.getUsername())) {
                    router.navigate(e, "View/profile.fxml");
                } else {
                    router.navigate(e, "View/user.fxml");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        nameBtn.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        Label usernameLabel = new Label("@" + post.getUsername());
        usernameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
        Label timeLabel = new Label(dateFormat.format(post.getTimestamp()));
        timeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
        hb.setAlignment(Pos.CENTER_LEFT);
        hb.getChildren().addAll(nameBtn, usernameLabel, timeLabel);

        vb.getChildren().add(hb);
        vb.getChildren().add(new Label(post.getContent()));

        HBox reactionHB = new HBox();
        reactionHB.setAlignment(Pos.CENTER);
        reactionHB.setSpacing(64);

        reactionHB.getChildren().addAll(
                getIconButton("Asset/like.png", ReactionType.LIKE, post.getId()),
                getIconButton("Asset/dislike.png", ReactionType.DISLIKE, post.getId()),
                getIconButton("Asset/love.png", ReactionType.LOVE, post.getId()),
                getIconButton("Asset/laugh.png", ReactionType.LAUGH, post.getId()),
                getIconButton("Asset/cry.png", ReactionType.CRY, post.getId()));

        vb.setStyle("-fx-padding: 0px 10px;");
        bp.setLeft(iv);
        bp.setCenter(vb);
        bp.setBottom(reactionHB);
        bp.getStyleClass().add("hover");
        bp.setStyle("-fx-padding: 8px 20px; -fx-border-width: 0 0 1 0; -fx-border-color: #eee;");

        return bp;
    }

    private void react(int postId, ReactionType reactionType, Button button) {
        BackendService backendService = new BackendService();
        backendService.reactToPost(postId, reactionType);
        updateButtonStyles(postId, reactionType);
    }

    private void updateButtonStyles(int postId, ReactionType selectedReaction) {
        Map<ReactionType, Button> reactions = postReactions.get(postId);

        if (reactions != null) {
            reactions.forEach((type, button) -> {
                if (type == selectedReaction) {
                    toggleButtonStyle(button, type);
                } else {
                    resetButtonColor(button);
                }
            });
        }
    }

    private void resetButtonColor(Button button) {
        button.setStyle("");
    }

    private void toggleButtonStyle(Button button, ReactionType reactionType) {
        String currentStyle = button.getStyle();
        boolean isSelected = currentStyle.contains("background-color");

        if (isSelected) {
            button.setStyle("");
        } else {
            String color = getColorForReactionType(reactionType);
            button.setStyle("-fx-background-color: " + color + ";");
        }
    }

    private String getColorForReactionType(ReactionType reactionType) {
        return switch (reactionType) {
            case LIKE -> "#add8e6";
            case DISLIKE -> "#ffa07a";
            case LOVE -> "#ff69b4";
            case LAUGH -> "#f0e68c";
            case CRY -> "#87ceeb";
        };
    }
}
