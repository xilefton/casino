package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.MockUser;
import ch.bbbaden.casino.Model;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;

public class AdminController implements Controller {
    public TabPane tabPane_overview;
    public ListView listViewPlayer;
    public Label label_username;
    public Label label_coins;
    public Label label_purchased;
    public Label label_bet;
    public ListView listView_games;
    public ListView listView_gameOverview;
    private AdminModel adminModel;

    @Override
    public void initialize(Model model) {
        adminModel = (AdminModel) model;

        listViewPlayer.getItems().addAll(adminModel.getUsers());
        listView_games.getItems().addAll(adminModel.getGames());

        listViewPlayer.setCellFactory(param -> new ListCell<MockUser>() {
            protected void updateItem(MockUser mockUser, boolean empty) {
                super.updateItem(mockUser, empty);
                if (empty || mockUser == null) {
                    setText("");
                } else {
                    setText(mockUser.toString());
                }
            }
        });

        listViewPlayer.getSelectionModel().selectedItemProperty().addListener((newValue) -> {
            if (null != listViewPlayer.getSelectionModel().getSelectedItem()) {
                adminModel.selectUser((MockUser) listViewPlayer.getSelectionModel().getSelectedItem());
            }
        });

        listView_games.getSelectionModel().selectedItemProperty().addListener((newValue) -> {
            if (null != listView_games.getSelectionModel().getSelectedItem()) {
                System.out.println((String) listView_games.getSelectionModel().getSelectedItem());
                adminModel.selectGame((String) listView_games.getSelectionModel().getSelectedItem());
            }
        });

        update();
    }

    @Override
    public void update() {
        listView_gameOverview.getItems().clear();
        listView_gameOverview.getItems().addAll(adminModel.getGameData());
        label_username.setText(adminModel.getUsername());
        label_bet.setText(Long.toString(adminModel.getBet()));
        label_coins.setText(Long.toString(adminModel.getCoins()));
        label_purchased.setText(Long.toString(adminModel.getPurchased()));
        System.out.println("updated");
    }

    public void btn_quit_onAction() {
        adminModel.logout();
    }
}
