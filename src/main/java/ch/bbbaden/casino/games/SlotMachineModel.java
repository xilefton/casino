package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;

import java.sql.SQLException;


public class SlotMachineModel extends Game {
    private NormalUser normalUser;
    public SlotMachineModel(NormalUser normalUser) {
        super("/fxml/SlotMachine.fxml", "Super Cherry", "/images/SuperCherry_Logo.png", normalUser);
        this.normalUser = normalUser;

    }

    public String getCoins() {
        try {
            return Integer.toString(normalUser.getCoins());
        } catch (SQLException e) {
            System.err.println(e);
        } return null;
    }
    public void addCoins() throws SQLException {
        int coins = normalUser.getCoins() - 1;
        normalUser.addCoins(coins, false);
    }
}
