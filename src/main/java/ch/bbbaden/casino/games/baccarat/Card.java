package ch.bbbaden.casino.games.baccarat;

public class Card {

    private String imagePath;
    private int points;

    public Card(String imagePath, int points) {
        this.imagePath = imagePath;
        this.points = points;
    }

    public String getImagePath() {
        return "/images/baccarat/" + imagePath;
    }

    public int getPoints() {
        return points;
    }
}
