module com.example.backupctg {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.backupctg to javafx.fxml;
    exports com.example.backupctg;
}