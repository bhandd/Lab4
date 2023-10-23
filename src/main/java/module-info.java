module grupp.LAB {
    requires javafx.controls;
    requires javafx.fxml;


    opens grupp.lab4 to javafx.fxml;
    exports grupp.lab4;
}