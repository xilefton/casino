package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.AdminUser;
import ch.bbbaden.casino.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLoggedInModel extends Model {
    private AdminUser adminUser;
    private Connection con;
    ResultSet rs;

    public AdminLoggedInModel(AdminUser adminUser) {
        super("/fxml/AdminLoggedIn.fxml", "Admin Logged In", true);
        this.adminUser = adminUser;
    }

    void sqlStatement() {
        try {
            rs = con.createStatement().executeQuery("SELECT `username` from `normalusers` WHERE `ID` = \"1\"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void openConnection() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/casino", "root", "");
    }

    String getUserAsString(int spalte) {
        return null;
    }
}
