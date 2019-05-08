package ch.bbbaden.casino.games.roulette;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;
import ch.bbbaden.casino.games.Game;

import java.sql.SQLException;

public class RouletteModel extends Game {
    public RouletteModel(NormalUser normalUser) {
        super("/fxml/Roulette.fxml", "Roulette","/images/Roulette_Logo.png", normalUser);

    }

    public int getCoins(){
        try {
            return getNormalUser().getCoins();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return 0;
    }


    public void addCoins(int coins) throws SQLException {
        getNormalUser().addCoins(coins, false);
    }

}