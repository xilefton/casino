package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.AdminUser;
import ch.bbbaden.casino.Model;

import java.sql.SQLException;

class AdminLoginModel extends Model {
    private AdminUser adminUser;

    private String username = "";
    private String password = "";

    AdminLoginModel() {
        super("/fxml/AdminLogin.fxml", "AdminLogin", true);
    }

    /*private void showErrorMessage(String message) {
        LoginFailedModel errView = new LoginFailedModel(message);
        changeScene(errView);
        if (errView.doRetry()) {
            username = "";
            password = "";
            show();
        } else {
            changeScene(new StartModel());
        }
        errView.close();
    }*/

    void login(String username, String password){
        boolean loginSuccessful = false;
        try {
            adminUser = new AdminUser();
            adminUser.login(username, password);
            loginSuccessful = true;
        } catch (SQLException ex) {
            showErrorMessage(ex.getLocalizedMessage(), "Error", ErrorType.NOTIFICATION);
        }

        if (loginSuccessful) {
            changeScene(new AdminModel(adminUser));
        }
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    void showStartMenu() {
        changeScene(new StartModel());
    }
}
