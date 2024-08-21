module com.example.aoopproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.aoopproject to javafx.fxml;
    exports com.example.aoopproject;
}