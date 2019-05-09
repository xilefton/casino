package ch.bbbaden.casino;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class User {

    private Connection con;
    private String username;
    private boolean admin;
    private PreparedStatement normalUserLogin;
    private PreparedStatement adminUserLogin;
    private PreparedStatement userQuery;
    private PreparedStatement adminUserQuery;

    User(boolean admin) throws SQLException {
        this.admin = admin;
        try {
            openConnection();
            prepareStatements();
        } catch (SQLException e) {
            throw new SQLException("Fehler beim verbinden zur Datenbank, überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut" + e);
        }
    }

    User(String username, boolean admin) throws SQLException {
        this.admin = admin;
        this.username = username;
        try {
            openConnection();
            prepareStatements();
        } catch (SQLException e) {
            throw new SQLException("Fehler beim verbinden zur Datenbank, überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut" + e);
        }
    }

    public void login(String username, String password) throws SQLException {
        this.username = username;

        ResultSet rs;

        if (userExists(username)) {
            if (admin) {
                adminUserLogin.setString(1, username);
                rs = adminUserLogin.executeQuery();
            } else {
                normalUserLogin.setString(1, username);
                rs = normalUserLogin.executeQuery();
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

    private void prepareStatements() throws SQLException {
        normalUserLogin = con.prepareStatement("SELECT password FROM `normalusers` WHERE username = ?");
        adminUserLogin = con.prepareStatement("SELECT password FROM `adminusers` WHERE username = ?");
        userQuery = con.prepareStatement("SELECT `username` FROM `normalusers`");
        adminUserQuery = con.prepareStatement("SELECT  `username` FROM `adminusers`");
    }

    protected Connection getConnection() {
        return con;
    }

    String calculateHashWithSalt(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        md.update((DatatypeConverter.printHexBinary(md.digest()).toUpperCase() + "$" + password).getBytes());
        return DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
    }

    void openConnection() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/casino", "root", "");
    }

    boolean userExists(String username) throws SQLException {
        ResultSet rs;
        if (admin) {
            rs = adminUserQuery.executeQuery();
        } else {
            rs = userQuery.executeQuery();
        }
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