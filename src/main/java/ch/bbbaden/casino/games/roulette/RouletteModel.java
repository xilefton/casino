package ch.bbbaden.casino.games.roulette;

import ch.bbbaden.casino.CoinChangeReason;
import ch.bbbaden.casino.NormalUser;
import ch.bbbaden.casino.games.Game;
import ch.bbbaden.casino.scenes.ErrorType;

import java.sql.SQLException;

public class RouletteModel extends Game {
    public RouletteModel(NormalUser normalUser) {
        super("/fxml/Roulette.fxml", "Roulette", "/images/Roulette_Logo.png", normalUser, "Roulette");

    }

    public long getCoins() {
        try {
            return getNormalUser().getCoins();
        } catch (SQLException e) {
            showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
        }
        return 0;
    }

    private void changeCoins(int amountOfCoins, CoinChangeReason coinChangeReason) {
        try {
            getNormalUser().changeCoins(amountOfCoins, coinChangeReason);
        } catch (SQLException e) {
            showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
        }
    }

    void changeCoinsBet(int amountOfCoins) {
        changeCoins(-amountOfCoins, CoinChangeReason.PLAYER_BET);
    }

    void changeCoinsWinOrLoss(int amountOfCoins) {
        changeCoins(amountOfCoins, CoinChangeReason.PLAYER_WIN_OR_LOSS);
    }
}
