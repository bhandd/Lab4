module grupp.LAB {
    requires javafx.controls;
    requires javafx.fxml;


    opens grupp.lab4 to javafx.fxml;
    exports grupp.lab4;
    exports grupp.lab4.model;
    opens grupp.lab4.model to javafx.fxml;
    exports grupp.lab4.view;
    opens grupp.lab4.view to javafx.fxml;
}