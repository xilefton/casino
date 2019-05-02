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
class RegisterFailedModel extends Model {

    private String errorMessage;
    private boolean retry = false;

    RegisterFailedModel(String errorMessage) {
        super("/fxml/RegisterFailed.fxml", "Register failed", false);
        this.errorMessage = errorMessage;
    }

    String getErrorMessage() {
        return errorMessage;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }

    boolean doRetry() {
        return retry;
    }
}
