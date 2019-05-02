package ch.bbbaden.casino.games;

import ch.bbbaden.casino.NormalUser;

public class TestGameModel extends Game {
    public TestGameModel(NormalUser normalUser) {
        super("/fxml/TestGame.fxml", "Test", "/images/roulette_logo.png", normalUser);
    }
}
