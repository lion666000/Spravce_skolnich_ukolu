module com.example.ukoly {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ukoly to javafx.fxml;
    exports com.example.ukoly;
}