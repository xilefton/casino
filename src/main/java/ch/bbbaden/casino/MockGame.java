package ch.bbbaden.casino;

public class MockGame {
    String name;
    String date;
    long bet;
    long achievement;

    public MockGame(String name, String date, long bet, long achievement) {
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
