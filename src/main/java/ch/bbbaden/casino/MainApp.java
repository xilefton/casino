package ch.bbbaden.casino;

import ch.bbbaden.casino.scenes.AdminLoggedInModel;
import ch.bbbaden.casino.scenes.StartModel;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws SQLException {
        Font.loadFont(getClass().getResourceAsStream("/fonts/casino.ttf"), 20);
        //new ModelManager(stage).setModel(new StartModel());

        AdminUser adminUser = new AdminUser();
        adminUser.login("FELIX", "felix");
        new ModelManager(stage).setModel(new AdminLoggedInModel(adminUser));
    }
}
