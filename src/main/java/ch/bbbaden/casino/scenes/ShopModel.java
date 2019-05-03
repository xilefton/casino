package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;

import java.sql.SQLException;

public class ShopModel extends Model {
    private NormalUser normalUser;

    public ShopModel() {
        super("/fxml/Shop.fxml", "Shop", false);
    }

    public int getCoins() {
        try {
            return normalUser.getCoins();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void buy(int value) {
        try {
            normalUser.addCoins(value, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        notifyController();
    }
}
