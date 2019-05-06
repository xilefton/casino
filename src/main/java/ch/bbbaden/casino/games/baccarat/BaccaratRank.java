package ch.bbbaden.casino.games.baccarat;

public enum BaccaratRank {
    ACE(1, "A"),
    TWO(2,"2"),
    THREE(3,"3"),
    FOUR(4,"4"),
    FIVE(5,"5"),
    SIX(6,"6"),
    SEVEN(7,"7"),
    EIGHT(8,"8"),
    NINE(9,"9"),
    TEN(0,"10"),
    JACK(0,"J"),
    QUEEN(0,"Q"),
    KING(0,"K");

    private final String rank;
    private final int points;

    BaccaratRank(int points, String rank) {
        this.points = points;
        this.rank = rank;
    }

    public int getPoints() {
        return points;
    }

    public String getRank() {
        return rank;
    }
}
