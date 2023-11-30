package com.example.yclient.Util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class NotificationUtil {

    public static void showNotification(String message) {
        var notificationStage = new Stage();
        notificationStage.initStyle(StageStyle.UNDECORATED);

        var root = new VBox(10);
        root.setAlignment(Pos.TOP_RIGHT);

        var notificationLabel = new Label(message);
        notificationLabel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px;");
        notificationLabel.setMinWidth(250);

        root.getChildren().add(notificationLabel);
        Scene scene = new Scene(root);
        notificationStage.setScene(scene);

        var timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(3000),
                        new KeyValue(notificationLabel.opacityProperty(),
                                0)
                )
        );
        double mainStageX = Router.getCurrentStage().getX();
        double mainStageY = Router.getCurrentStage().getY();

        notificationStage.setX(mainStageX + 40);
        notificationStage.setY(mainStageY + 40);

        timeline.setOnFinished(event -> {
            notificationStage.close();

        });
        timeline.play();
        scene.setFill(null);
        notificationStage.show();
    }
}
