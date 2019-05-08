package ch.bbbaden.casino.games.roulette;

import ch.bbbaden.casino.CoinChangeReason;
import ch.bbbaden.casino.NormalUser;
import ch.bbbaden.casino.games.Game;

import java.sql.SQLException;

public class RouletteModel extends Game {
    public RouletteModel(NormalUser normalUser) {
        super("/fxml/Roulette.fxml", "Roulette","/images/Roulette_Logo.png", normalUser, "Roulette");

    }

    public long getCoins(){
        try {
            return getNormalUser().getCoins();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return 0;
    }


    public void addCoins(int coins, CoinChangeReason coinChangeReason) throws SQLException {
        getNormalUser().changeCoins(coins, coinChangeReason);
    }

}
