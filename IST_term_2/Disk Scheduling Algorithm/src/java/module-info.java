module com.example.systemyoperacyjnezadanie2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.systemyoperacyjnezadanie2 to javafx.fxml;
    exports com.example.systemyoperacyjnezadanie2;
}