module com.example.integradora3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.integradora3 to javafx.fxml;
    exports com.example.integradora3;
    exports com.example.integradora3.control;
    opens com.example.integradora3.control to javafx.fxml;
    exports com.example.integradora3.model;
    opens com.example.integradora3.model to javafx.fxml;
}