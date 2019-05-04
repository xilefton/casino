package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.CoinChangeReason;
import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;

import java.sql.SQLException;

public class ShopModel extends Model {
    private NormalUser normalUser;

    public ShopModel(NormalUser normalUser) {
        super("/fxml/Shop.fxml", "Shop", false);
        this.normalUser = normalUser;
    }

    public long getCoins() {
        try {
            return normalUser.getCoins();
        } catch (SQLException e) {
            showErrorMessage("Fehler beim einlesen aus der Datenbank überprüfen Sie ihre Internetverbindung.", "Verbindungsfehler", ErrorType.CONNECTION);
        }
        return 0;
    }

    public void buy(int value) {
        try {
            normalUser.changeCoins(value, CoinChangeReason.PLAYER_PURCHASE);
        } catch (SQLException e) {
            showErrorMessage("Fehler beim einlesen aus der Datenbank überprüfen Sie ihre Internetverbindung.", "Verbindungsfehler", ErrorType.CONNECTION);
        }
        notifyController();
    }
}
