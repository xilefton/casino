package ch.bbbaden.casino.games.baccarat;

public enum BaccaratSuit {
    DIAMONDS("D"),
    CLUBS("C"),
    HEARTS("H"),
    SPADES("S");

    private final String suit;

    BaccaratSuit(String suit) {
        this.suit = suit;
    }

    public String getSuit() {
        return suit;
    }
}
