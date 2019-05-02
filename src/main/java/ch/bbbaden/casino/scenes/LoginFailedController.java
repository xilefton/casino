/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author felix
 */
public class LoginFailedController implements Controller {

    @FXML
    private Label errorMessage;

    private LoginFailedModel loginFailedModel;

    public void initialize(Model model) {
        loginFailedModel = (LoginFailedModel) model;
        update();
    }

    public void update() {
        errorMessage.setText(loginFailedModel.getErrorMessage());
    }

    public void button_abort_onAction() {
        loginFailedModel.setRetry(false);
        loginFailedModel.close();
    }

    public void button_retry_onAction() {
        loginFailedModel.setRetry(true);
        loginFailedModel.close();
    }
}
