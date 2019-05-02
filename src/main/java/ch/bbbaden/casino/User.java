package ch.bbbaden.casino;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class User {

    Connection connie;
    private String username, password;
    private Statement statement;
    private ResultSet rs;
    private boolean admin;

    User(boolean admin) {
        this.admin = admin;
    }

    public void login(String username, String password) throws SQLException {
        this.username = username;
        this.password = password;

        openConnection();

        statement = connie.createStatement();
        if (admin) {
            rs = statement.executeQuery("SELECT * FROM `adminusers`");
        } else {
            rs = statement.executeQuery("SELECT * FROM `normalusers`");
        }

        while (rs.next()) {
            if (rs.getString(1).equals(username)) {
                try {
                    if (calculateHashWithSalt(password).equals(rs.getString(2))) {
                        return;
                    } else {
                        throw new SQLException("Falsches Passwort");
                    }
                } catch (NoSuchAlgorithmException e) {
                    System.err.println(e);
                }
            }
        }
        throw new SQLException("Benutzer nicht gefunden");
    }


    String calculateHashWithSalt(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        md.update((DatatypeConverter.printHexBinary(md.digest()).toUpperCase() + "$" + password).getBytes());
        return DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
    }

    void openConnection() throws SQLException {
        connie = DriverManager.getConnection("jdbc:mysql://localhost:3306/casino", "root", "");
    }

    boolean userExists(String username) throws SQLException {
        statement = connie.createStatement();
        rs = statement.executeQuery("SELECT * FROM `normalusers`");
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