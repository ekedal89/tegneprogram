module org.example.tegneprogram {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tegneprogram to javafx.fxml;
    exports org.example.tegneprogram;
}