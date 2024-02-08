module ca1.ca1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca1.ca1 to javafx.fxml;
    exports ca1.ca1;
}