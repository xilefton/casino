package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;

import java.sql.SQLException;

class RegisterModel extends Model {

    RegisterModel() {
        super("/fxml/register.fxml", "register", true);
    }

    void register(String username, String password, int coins) {
        if (username.length() >= 20) {
            showErrorMessage("der Benutzername ist zu lang", "Error when Registering", ErrorType.NOTIFICATION);
        }
        NormalUser normalUser = new NormalUser();
        try {
            normalUser.register(username, password, coins);
            normalUser.login(username, password);
            changeScene(new HomeModel(normalUser));
        } catch (SQLException ex) {
            showErrorMessage(ex.getLocalizedMessage(), "Error when Registering", ErrorType.NOTIFICATION);
        }
    }

    public void showStartMenu() {
        changeScene(new StartModel());
    }
}
