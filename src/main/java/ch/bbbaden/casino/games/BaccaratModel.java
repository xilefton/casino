package ch.bbbaden.casino.games;

import ch.bbbaden.casino.NormalUser;

import java.sql.SQLException;

public class BaccaratModel extends Game{

    private NormalUser normalUser;
    private int selectedBet;

    public BaccaratModel(NormalUser normalUser) {
        super("/fxml/Baccarat.fxml", "Baccarat by Felix", "/images/Baccarat_Logo.png", normalUser);
        this.normalUser = normalUser;
    }

    public String getCoins() {
        try {
            return Integer.toString(normalUser.getCoins());
        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    public void updateCoins(int selectedBet, boolean purchased) throws SQLException {
        this.selectedBet = selectedBet;
        normalUser.addCoins(this.selectedBet, purchased);
    }
}