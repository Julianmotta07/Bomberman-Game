module com.example.prueba222 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.integradora3 to javafx.fxml;
    exports com.example.integradora3;
}