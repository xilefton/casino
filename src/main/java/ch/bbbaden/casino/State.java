package ch.bbbaden.casino;

public class State {
    private long coins;
    private boolean changed = true;
    private long playerPurchase, playerBet;

    public State(long coins, long playerPurchase, long playerBet) {
        this.coins = coins;
        this.playerPurchase = playerPurchase;
        this.playerBet = playerBet;
    }

    public long getCoins() {
        return coins;
    }

    public boolean isChanged() {
        return changed;
    }

    public long getPlayerPurchase() {
        return playerPurchase;
    }

    public long getPlayerBet() {
        return playerBet;
    }
}
