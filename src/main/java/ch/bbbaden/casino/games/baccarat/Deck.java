package ch.bbbaden.casino.games.baccarat;

import java.util.ArrayList;

public class Deck {

    ArrayList<Card> deck = new ArrayList();

    public Deck() {
    }

    public ArrayList createDeck() {
        deck.clear();
        for ( BaccaratSuit suit : BaccaratSuit.values()) {
            for ( BaccaratRank rank : BaccaratRank.values()) {
                deck.add(new Card(rank.getRank() + suit.getSuit() + ".png", rank.getPoints()));
            }
        }
        return deck;
    }
}
