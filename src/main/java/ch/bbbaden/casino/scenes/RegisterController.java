/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * FXML Controller class
 *
 * @author felix
 */
public class RegisterController implements Controller {

    public TextField username;
    public Button register;
    public PasswordField password;
    public Spinner<Integer> startCoins;
    private RegisterModel registerModel;

    public void initialize(Model model) {
        registerModel = (RegisterModel) model;

        username.textProperty().addListener((ov, oldValue, newValue) -> {
            if (username.getText() != null) {
                username.setText(newValue.toUpperCase());
                if (username.getLength() >= 15) {
                    username.setText(oldValue);
                }
            }
        });
        username.setOnKeyPressed(ke -> {
            if (username.getText() != null) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    password.requestFocus();
                    password.selectAll();
                }
            }
        });
        password.setOnKeyPressed(ke -> {
            if (password.getText() != null) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    startCoins.requestFocus();
                }
            }
        });
        startCoins.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                if (startCoins.getValue().equals("") && String.valueOf(startCoins.getValue()).matches("\\d+") && startCoins.getValue() != null) {
                    if (username.getText().equals("")) {
                        startCoins.requestFocus();
                    } else {
                        if (username.getText().equals("")) {
                            username.requestFocus();
                        } else if (password.getText().equals("")) {
                            password.requestFocus();
                        } else {
                            on_register();
                        }
                    }
                } else {
                    startCoins.requestFocus();
                }
            }
        });
    }

    public void update() {

    }

    public void on_register() throws NumberFormatException {
        if (username.getText().equals("")) {
            username.requestFocus();
        } else if (password.getText().equals("")) {
            password.requestFocus();
        } else if (startCoins.getValue() == null) {
            startCoins.requestFocus();
        } else {
            registerModel.register(username.getText(), password.getText(), startCoins.getValue());
        }
    }

    public void btn_back_onAction(ActionEvent actionEvent) {
        registerModel.showStartMenu();
    }
}
