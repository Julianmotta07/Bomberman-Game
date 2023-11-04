module com.example.prueba222 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.prueba222 to javafx.fxml;
    exports com.example.prueba222;
}