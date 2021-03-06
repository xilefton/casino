package ch.bbbaden.casino;


import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NormalUser extends User {

    private long coins;
    private boolean changed = true;
    private long playerPurchase, playerBet;
    private PreparedStatement recordStatement;
    private PreparedStatement registerStatement;
    private PreparedStatement betUpdateStatement;
    private PreparedStatement purchaseStatement;
    private PreparedStatement coinUpdateStatement;
    private PreparedStatement updateValuesStatement;

    public NormalUser() throws SQLException {
        super(false);
        prepareStatements();
    }

    public void register(String username, String password, int coins) throws SQLException {
        openConnection();
        if (!userExists(username)) {
            try {
                registerStatement.setString(1, username);
                registerStatement.setString(2, calculateHashWithSalt(password));
                registerStatement.setLong(3, coins);
                registerStatement.setLong(4, coins);
                registerStatement.executeUpdate();
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

        if (this.coins + change < 0) throw new SQLException("Sie können nicht mehr ausgeben als Sie haben");
        this.coins += change;

        coinUpdateStatement.setLong(1, coins);
        coinUpdateStatement.setString(2, super.getUsername());
        coinUpdateStatement.executeUpdate();

        switch (changeReason) {
            case PLAYER_BET:
                playerBet += coins;
                betUpdateStatement.setLong(1, playerBet);
                betUpdateStatement.setString(2, super.getUsername());
                betUpdateStatement.executeUpdate();
                break;
            case PLAYER_PURCHASE:
                playerPurchase += coins;
                purchaseStatement.setLong(1, playerPurchase);
                purchaseStatement.setString(2, super.getUsername());
                purchaseStatement.executeUpdate();
                break;
            default:
                break;
        }
        changed = true;
    }

    public State getState() {
        return new State(coins, playerBet);
    }

    public void recordChanges(String game, State oldState) throws SQLException {
        recordStatement.setString(1, game);
        System.out.println((oldState.getPlayerBet() - this.playerBet));
        recordStatement.setLong(2, (oldState.getPlayerBet() - this.playerBet));
        recordStatement.setLong(3, (oldState.getCoins() - coins));
        recordStatement.setString(4, new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        recordStatement.executeUpdate();
    }

    private void updateValues() throws SQLException {
        if (changed) {
            updateValuesStatement.setString(1, super.getUsername());
            ResultSet rs = updateValuesStatement.executeQuery();
            rs.next();
            coins = rs.getLong(1);
            playerBet = rs.getLong(2);
            playerPurchase = rs.getLong(3);
            changed = false;
        }
    }

    private void prepareStatements() throws SQLException {
        updateValuesStatement = getConnection().prepareStatement("SELECT `coins`, `purchased`, `bet` FROM `normalusers` WHERE username = ?");
        betUpdateStatement = getConnection().prepareStatement("UPDATE `normalusers` SET `bet` = ? WHERE `username` = ?");
        recordStatement = getConnection().prepareStatement("INSERT INTO `games`(`game`, `bet`, `achievement`, `date`) VALUES ( ?, ?, ?, ?)");
        registerStatement = getConnection().prepareStatement("INSERT INTO `normalusers`(`username`, `password`, `coins`, `purchased`) VALUES (?,?,?,?)");
        purchaseStatement = getConnection().prepareStatement("UPDATE `normalusers` SET `purchased` = ? WHERE `username` = ?");
        coinUpdateStatement = getConnection().prepareStatement("UPDATE `normalusers` SET `coins` = ? WHERE `username` = ?");
    }

    public long getCoins() throws SQLException {
        updateValues();
        return coins;
    }
}
