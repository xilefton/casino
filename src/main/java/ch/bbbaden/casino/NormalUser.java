package ch.bbbaden.casino;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NormalUser extends User {

    private long coins;
    private boolean changed = true;
    private long playerPurchase, playerBet;

    public NormalUser() {
        super(false);
    }

    public void register(String username, String password, int coins) throws SQLException {
        openConnection();
        if (!userExists(username)) {
            try {
                getConnection().createStatement().executeUpdate("INSERT INTO `normalusers`(`username`, `password`, `coins`, `purchased`) VALUES ('" + username + "','" + calculateHashWithSalt(password) + "','" + coins + "','" + coins + "')");
            } catch (NoSuchAlgorithmException e) {
                throw new SQLException("Fehler beim verarbeiten der eingabe");
            } catch (SQLException e) {
                throw new SQLException("Fehler beim verbinden mit dem Server überprüfen Sie ihre Internet-Verbindung");
            }
        } else {
            throw new SQLException("User existiert bereits");
        }
    }

    public void changeCoins(long change, CoinChangeReason changeReason) throws SQLException {
        updateValues();

        if (this.coins + change < 0) throw new SQLException("Coins value after change is smaller than null");
        this.coins += change;

        getConnection().createStatement().executeUpdate("UPDATE `normalusers` SET `coins` = '" + this.coins + "' WHERE `username` = '" + super.getUsername() + "'");

        switch (changeReason) {
            case PLAYER_BET:
                playerBet += coins;
                getConnection().createStatement().executeUpdate("UPDATE `normalusers` SET `bet` = '" + playerBet + "' WHERE `username` = '" + super.getUsername() + "'");
                break;
            case PLAYER_PURCHASE:
                playerPurchase += coins;
                getConnection().createStatement().executeUpdate("UPDATE `normalusers` SET `purchased` = '" + playerPurchase + "' WHERE `username` = '" + super.getUsername() + "'");
                break;
            default:
                break;
        }

        changed = true;
    }

    public State getState() {
        return new State(coins, playerPurchase, playerBet);
    }

    public void recordChanges(String game, State oldState) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            getConnection().createStatement().executeUpdate("INSERT INTO `games`(`game`, `bet`, `achievement`, `date`) VALUES ( \"" + game + "\", \"" +
                    (this.playerBet - oldState.getPlayerBet()) + "\", \"" + (oldState.getCoins() - coins) + "\", \"" + new SimpleDateFormat("yyyy/MM/dd").format(new Date()) + "\")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateValues() throws SQLException {
        if (changed) {
            ResultSet rs = getConnection().createStatement().executeQuery("SELECT `coins`, `purchased`, `bet` FROM `normalusers` WHERE username = \"" + super.getUsername() + "\"");
            rs.next();
            coins = rs.getLong(1);
            playerBet = rs.getLong(2);
            playerPurchase = rs.getLong(3);
            changed = false;
        }
    }

    public long getCoins() throws SQLException {
        updateValues();
        return coins;
    }

    public long getBet() throws SQLException {
        updateValues();
        return playerBet;
    }

    public long getPlayerPurchase() throws SQLException {
        updateValues();
        return playerPurchase;
    }
}
