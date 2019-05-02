package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;

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
}
