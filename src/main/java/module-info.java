module com.example.yclient {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.yclient to javafx.fxml;
    exports com.example.yclient;
}