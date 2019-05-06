package ch.bbbaden.casino;

import ch.bbbaden.casino.games.Baccarat.BaccaratModel;
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
        NormalUser normalUser = new NormalUser();
        normalUser.login("OTTO", "otto");
        //new ModelManager(stage).setModel(new HomeModel(normalUser));
        new ModelManager(stage).setModel(new StartModel());
        //NormalUser normalUser = new NormalUser();
        //normalUser.login("FELIX", "xilefton");
        //new ModelManager(stage).setModel(new BaccaratModel(normalUser));
    }
}
