package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLoggedInController implements Controller {
    AdminLoggedInModel adminLoggedInModel;
    public ListView listViewPlayer;
    public Label coins;
    public Label bought;
    public Label spent;
    ResultSet rs;

    @Override
    public void initialize(Model model) {
        adminLoggedInModel = (AdminLoggedInModel) model;
        try {
            adminLoggedInModel.openConnection();
            adminLoggedInModel.sqlStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        update();
    }

    @Override
    public void update() {
    }
}
