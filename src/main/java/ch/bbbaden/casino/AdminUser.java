package ch.bbbaden.casino;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminUser extends User {

    private ArrayList users;
    private PreparedStatement normalUsersStatement;
    private PreparedStatement gamesStatement;
    private ArrayList<MockGame> games;
    private PreparedStatement gameDataStatement;

    public AdminUser() throws SQLException {
        super(true);
        prepareStatements();
    }

    public ArrayList<MockUser> getUsers() throws SQLException {
        if (users == null)
            updateUsers();
        return users;
    }

    private void prepareStatements() throws SQLException {
        normalUsersStatement = getConnection().prepareStatement("SELECT `username` FROM `normalusers`");
        gamesStatement = getConnection().prepareStatement("SELECT * FROM `games`");
        gameDataStatement = getConnection().prepareStatement("SELECT  `date`, `àchievement`, `bet` WHERE `game` = ?");
    }

    public void updateUsers() throws SQLException {
        users = new ArrayList();
        ResultSet rs = normalUsersStatement.executeQuery();
        while (rs.next()) {
            users.add(new MockUser(rs.getString(1)));
        }
    }

    public ArrayList<MockGame> getGames() throws SQLException {
        if (games == null) {
            ResultSet games = gamesStatement.executeQuery();
            this.games = new ArrayList<>();
            while (games.next()) {
                this.games.add(new MockGame(games.getString(2), games.getString(5), games.getLong(3), games.getLong(4)));
            }
        }
        return this.games;
    }
}
