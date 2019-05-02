/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;

import java.sql.SQLException;

/**
 * @author greut
 */
class LoginModel extends Model {
    private NormalUser normalUser;

    private String username = "";
    private String password = "";

    LoginModel() {
        super("/fxml/Login.fxml", "Login", true);
    }

    private void showErrorMessage(String message) {
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
    }

    void login(String username, String password) {
        boolean loginSuccessful = false;
        normalUser = new NormalUser();
        try {
            normalUser.login(username, password);
            loginSuccessful = true;
        } catch (SQLException ex) {
            showErrorMessage(ex.getLocalizedMessage());
        }

        if (loginSuccessful) {
            changeScene(new HomeModel(normalUser));
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