module org.example.mazebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    // Ces lignes ouvrent TES packages à JavaFX FXML
    opens com.jmc.mazebank to javafx.fxml;
    opens com.jmc.mazebank.Controllers to javafx.fxml;
    opens com.jmc.mazebank.Controllers.Admin to javafx.fxml;
    opens com.jmc.mazebank.Controllers.Client to javafx.fxml;

    exports com.jmc.mazebank;
    exports com.jmc.mazebank.Controllers;
    exports com.jmc.mazebank.Models;
    exports com.jmc.mazebank.Controllers.Client;
    exports com.jmc.mazebank.Views;
}