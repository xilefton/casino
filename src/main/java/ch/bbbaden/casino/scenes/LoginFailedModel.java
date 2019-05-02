/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Model;

/**
 * @author greut
 */
class LoginFailedModel extends Model {

    private String errorMessage;
    private boolean retry = false;

    LoginFailedModel(String errorMessage) {
        super("/fxml/LoginFailed.fxml", "Login failed", false);
        this.errorMessage = errorMessage;
    }

    String getErrorMessage() {
        return errorMessage;
    }

    void setRetry(boolean retry) {
        this.retry = retry;
    }

    boolean doRetry() {
        return retry;
    }
}
