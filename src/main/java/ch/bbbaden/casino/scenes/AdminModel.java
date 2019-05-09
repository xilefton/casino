package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.AdminUser;
import ch.bbbaden.casino.MockGame;
import ch.bbbaden.casino.MockUser;
import ch.bbbaden.casino.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminModel extends Model {
    private AdminUser adminUser;
    private MockUser selectedUser;
    private ObservableList<MockUser> users;
    private String selectedGame;

    public AdminModel(AdminUser adminUser) {
        super("/fxml/Admin.fxml", "Admin Logged In", true);
        this.adminUser = adminUser;
    }

    ObservableList<MockUser> getUsers() {
        updateAllUsers();
        return users;
    }

    void selectUser(MockUser mockUser) {
        System.out.println(mockUser);
        selectedUser = mockUser;
        notifyController();
    }

    String getUsername() {
        if (selectedUser != null) {
            return selectedUser.getUsername();
        } else return "Kein Benutzer ausgewählt";
    }

    void logout() {
        changeScene(new StartModel());
    }

    long getBet() {
        try {
            if (selectedUser != null) {
                return selectedUser.getPlayerBet();
            }
        } catch (SQLException e) {
            showConnectionError(e);
        }
        return 0;
    }

    private void showConnectionError(SQLException e) {
        showErrorMessage("Fehler beim einlesen der Datenbank: " + e.toString(), "Verbindung Fehlgeschlagen", ErrorType.NOTIFICATION);
    }

    void deleteSelectedUser() {
        try {
            users.remove(selectedUser);
            selectedUser.delete();
        } catch (SQLException e) {
            showConnectionError(e);
        }
        notifyController();
    }

    long getCoins() {
        try {
            if (selectedUser != null) {
                return selectedUser.getCoins();
            } else return 0;
        } catch (SQLException e) {
            showConnectionError(e);
        }
        return 0;
    }

    long getPurchased() {
        try {
            if (selectedUser != null) {
                return selectedUser.getPlayerPurchase();
            } else return 0;
        } catch (SQLException e) {
            showConnectionError(e);
        }
        return 0;
    }

    void updateAllUsers() {
        users = FXCollections.observableArrayList();
        try {
            users.addAll(adminUser.getUsers());
        } catch (SQLException e) {
            showConnectionError(e);
        }
    }

    public void updateUser() {
        try {
            adminUser.updateUsers();
        } catch (SQLException e) {
            showConnectionError(e);
        }
    }

    public void selectGame(String item) {
        selectedGame = item;
        notifyController();
    }

    public String[] getGames() {
        return new String[]{"Baccarat", "pennypusher", "Roulette", "supercherry"};
    }

    public ArrayList<String> getGameData() {
        ArrayList<String> gameData = new ArrayList<>();
        try {
            for (MockGame mockGame : adminUser.getGames()) {
                if (mockGame.getName().equals(selectedGame)) gameData.add(mockGame.toString());
            }
        } catch (SQLException e) {
            showConnectionError(e);
        }
        if (gameData.isEmpty()) {
            gameData.add("Keine Erfassten Daten");
        }
        return gameData;
    }
}
