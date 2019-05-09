package ch.bbbaden.casino;

public class MockGame {
    private String name;
    private String date;
    private long bet;
    private long achievement;

    MockGame(String name, String date, long bet, long achievement) {
        this.name = name;
        this.date = date;
        this.bet = bet;
        this.achievement = achievement;
    }

    public String toString() {
        return date + ": unser gewinn: " + -achievement + " Benutzer hat eingesetzt: " + bet;
    }

    public String getName() {
        return name;
    }
}
