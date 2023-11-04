module com.example.demojavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.demojavafx to javafx.fxml, com.google.gson;
    exports com.example.demojavafx;
    exports com.example.demojavafx.control;
    opens com.example.demojavafx.control to com.google.gson, javafx.fxml;
}