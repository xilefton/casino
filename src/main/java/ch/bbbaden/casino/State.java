package ch.bbbaden.casino;

public class State {
    private long coins;
    private long playerBet;

    State(long coins, long playerBet) {
        this.coins = coins;
        this.playerBet = playerBet;
    }

    public long getCoins() {
        return coins;
    }

    long getPlayerBet() {
        return playerBet;
    }
}
