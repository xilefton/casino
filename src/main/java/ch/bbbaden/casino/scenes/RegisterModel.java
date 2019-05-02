package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;

import java.sql.SQLException;

class RegisterModel extends Model {

    RegisterModel() {
        super("/fxml/register.fxml", "register", true);
    }

    private void showErrorMessage(String message) {
        RegisterFailedModel errView = new RegisterFailedModel(message);
        changeScene(errView);
        if (!errView.doRetry()) {
            changeScene(new StartModel());
        }
        errView.close();
    }

    void register(String username, String password, int coins) {
        if (username.length() >= 20) {
            showErrorMessage("der Benutzername ist zu lang");
        }
        NormalUser normalUser = new NormalUser();
        try {
            normalUser.register(username, password, coins);
            normalUser.login(username, password);
            changeScene(new HomeModel(normalUser));
        } catch (SQLException ex) {
            showErrorMessage(ex.getLocalizedMessage());
        }
    }
}
