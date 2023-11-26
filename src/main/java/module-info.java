module com.example.yclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires jbcrypt;

    opens com.example.yclient to javafx.fxml;
    exports com.example.yclient;
    exports com.example.yclient.Controller;
    opens com.example.yclient.Controller to javafx.fxml;
    opens com.example.yclient.Model.responses to com.google.gson;
    opens com.example.yclient.Model to com.google.gson;
}