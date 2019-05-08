package ch.bbbaden.casino;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private Connection con;
    private String username, password;
    private boolean admin;

    User(boolean admin) {
        this.admin = admin;
    }

    public void login(String username, String password) throws SQLException {
        this.username = username;
        openConnection();

        ResultSet rs;

        if (userExists(username)) {
            if (admin) {
                rs = con.createStatement().executeQuery("SELECT password FROM `adminusers` WHERE username = \"" + username + "\"");
            } else {
                rs = con.createStatement().executeQuery("SELECT password FROM `normalusers` WHERE username = \"" + username + "\"");
            }
        } else {
            throw new SQLException("Benutzer nicht gefunden oder Passwort falsch");
        }

        try {
            rs.next();
            if (!calculateHashWithSalt(password).equals(rs.getString(1))) {
                throw new SQLException("Benutzer nicht gefunden oder Passwort Falsch");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new SQLException("Fehler beim verarbeiten der eingabe");
        }
    }

    protected Connection getConnection() {
        return con;
    }

    String calculateHashWithSalt(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        //md.update((DatatypeConverter.printHexBinary(md.digest()).toUpperCase() + "$" + password).getBytes());
        return DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
    }

    void openConnection() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/casino", "root", "");
    }

    boolean userExists(String username) throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("SELECT `username` FROM `normalusers`");
        while (rs.next()) {
            if (rs.getString(1).equals(username)) {
                return true;
            }
        }
        return false;
    }

    String getUsername() {
        return username;
    }
}