package ch.bbbaden.casino;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MockUser extends User {

    private final PreparedStatement updateValuesStatement;
    private final PreparedStatement deleteUserStatement;
    private long coins;
    private long playerBet;
    private long playerPurchase;
    private boolean upToDate = false;

    MockUser(String username) throws SQLException {
        super(username, false);
        updateValuesStatement = getConnection().prepareStatement("SELECT `coins`, `purchased`, `bet` FROM `normalusers` WHERE username = ?");
        deleteUserStatement = getConnection().prepareStatement("DELETE FROM `normalusers` WHERE `normalusers`.`username` = ?");
    }

    private void updateValues() throws SQLException {
        if (!upToDate) {
            updateValuesStatement.setString(1, super.getUsername());
            ResultSet rs = updateValuesStatement.executeQuery();
            rs.next();
            coins = rs.getLong(1);
            playerBet = rs.getLong(2);
            playerPurchase = rs.getLong(3);
            upToDate = true;
        }
    }

    public long getCoins() throws SQLException {
        updateValues();
        return coins;
    }

    public long getPlayerBet() throws SQLException {
        updateValues();
        return playerBet;
    }

    public long getPlayerPurchase() throws SQLException {
        updateValues();
        return playerPurchase;
    }

    @Override
    public String toString() {
        return getUsername();
    }

    public String getUsername() {
        return super.getUsername();
    }

    public void setUpToDate(boolean upToDate) {
        this.upToDate = upToDate;
    }

    public void delete() throws SQLException {
        deleteUserStatement.setString(1, getUsername());
        deleteUserStatement.executeUpdate();
    }
}
