package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;
import ch.bbbaden.casino.State;
import ch.bbbaden.casino.games.Game;
import ch.bbbaden.casino.games.TestGame2Model;
import ch.bbbaden.casino.games.TestGameModel;
import javafx.scene.image.Image;

import java.sql.SQLException;


public class HomeModel extends Model {

    private NormalUser normalUser;
    private Game[] games;
    private int currIndex = 0;

    public HomeModel(NormalUser normalUser) {
        super("/fxml/Home.fxml", "Welcome", true);
        this.normalUser = normalUser;
        games = new Game[]{new TestGameModel(normalUser), new TestGame2Model(normalUser)};
    }

    String getCoins() {
        try {
            return Long.toString(normalUser.getCoins());
        } catch (SQLException e) {
            showErrorMessage("Fehler beim einlesen aus der Datenbank überprüfen Sie ihre Internetverbindung.", "Verbindungsfehler", ErrorType.CONNECTION);
        }
        return null;
    }

    void playGame() {
        State saveState = normalUser.getState();
        String game = games[currIndex].getName();
        hide();
        changeScene(games[currIndex]);
        show();
        try {
            normalUser.recordChanges(game, saveState);
        } catch (SQLException e) {
            showErrorMessage("Fehler beim einlesen aus der Datenbank überprüfen Sie ihre Internetverbindung.", "Verbindungsfehler", ErrorType.CONNECTION);
        }
    }

    void changeGame(int indexOffset) {
        if (currIndex + indexOffset < 0) {
            currIndex = games.length + (currIndex + indexOffset);
        } else if (currIndex + indexOffset >= games.length) {
            currIndex = -(games.length - (currIndex + indexOffset));
        } else {
            currIndex += indexOffset;
        }
        notifyController();
    }

    Image getImage() {
        return games[currIndex].getImage();
    }

    public void showShop() {
        hide();
        changeScene(new ShopModel(normalUser));
        show();
    }
}
