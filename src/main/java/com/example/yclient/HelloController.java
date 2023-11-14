package com.example.yclient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    private int count = 0;

    @FXML
    private Label label;

    @FXML
    protected void onHelloButtonClick() {
        label.setText("Count: " + ++count);
    }
}