package ch.bbbaden.casino;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NormalUser extends User {

    private int coins;
    private int purchasedCoins;
    private boolean changed = true;
    private String update;
    private ResultSet rs;
    private Statement st;

    public NormalUser() {
        super(false);
    }

    public void addCoins(int coins, boolean purchased) throws SQLException {
        updateValues();
        this.coins += coins;
        st = connie.createStatement();
        update = "UPDATE `normalusers` SET `coins` = '" + this.coins + "' WHERE `username` = '" + super.getUsername() + "'";
        st.executeUpdate(update);
        if (purchased) {
            purchasedCoins += coins;
            update = "UPDATE `normalusers` SET `purchased` = '" + purchasedCoins + "' WHERE `username` = '" + super.getUsername() + "'";
            st.executeUpdate(update);
        }
        changed = true;
    }

    private void updateValues() throws SQLException {
        if (changed) {
            st = connie.createStatement();
            rs = st.executeQuery("SELECT `coins` FROM `normalusers` WHERE `username` = \"" + super.getUsername() + "\"");
            rs.next();
            coins = Integer.parseInt(rs.getString(1));
            rs = st.executeQuery("SELECT `purchased` FROM `normalusers` WHERE `username` = \"" + super.getUsername() + "\"");
            rs.next();
            purchasedCoins = Integer.parseInt(rs.getString(1));
            changed = false;
        }
    }

    public int getCoins() throws SQLException {
        updateValues();
        return coins;
    }

    public int getPurchasedCoins() throws SQLException {
        updateValues();
        return purchasedCoins;
    }

    public void register(String username, String password, int coins) throws SQLException {
        openConnection();
        if (!userExists(username)) {
            try {
                try {
                    update = "INSERT INTO `normalusers`(`username`, `password`, `coins`, `purchased`) VALUES ('" + username + "','" + calculateHashWithSalt(password) + "','" + coins + "','" + coins + "')";
                } catch (NoSuchAlgorithmException e) {
                    throw new SQLException("damn critical error");
                }
                st = connie.createStatement();
                st.executeUpdate(update);
            } catch (SQLException ex) {
                throw new SQLException("Fehler bei der Kommunikation mit der Datenbank");
            }
        } else {
            throw new SQLException("User existiert bereits");
        }
    }

}
