package ch.bbbaden.casino.games.Baccarat;

import ch.bbbaden.casino.NormalUser;
import ch.bbbaden.casino.games.Game;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class BaccaratModel extends Game {

    private NormalUser normalUser;
    private int selectedBet, cardsValue;
    private Deck deck = new Deck();
    private Card card;
    private ArrayList<Card> cards = new ArrayList();
    private Random random = new Random();

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

    public void createDeck() {
        cards = deck.createDeck();
    }

    public String getRandomCard() {
        card = cards.get(random.nextInt(cards.size()));
        cards.remove(card);
        return card.getImagePath();
    }
}