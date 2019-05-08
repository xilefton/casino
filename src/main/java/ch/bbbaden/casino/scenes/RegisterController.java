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
    public TextField password;
    public TextField startCoins;
    private RegisterModel registerModel;

    public void initialize(Model model) {
        registerModel = (RegisterModel) model;

        username.textProperty().addListener((ov, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                if (username.getLength() > 15 || !newValue.matches("^[a-zA-Z0-9]*$")) {
                    username.setText(oldValue);
                } else {
                    username.setText(newValue.toUpperCase());
                }
            }
        });

        password.textProperty().addListener((ov, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                if (password.getLength() > 20 || !newValue.matches("^[a-zA-Z0-9?!$£@,.*]*$")) {
                    password.setText(oldValue);
                }
            }
        });

        username.setOnKeyPressed(ke -> {
            if (!username.getText().equals("")) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    password.requestFocus();
                    password.selectAll();
                }
            }
        });

        password.setOnKeyPressed(ke -> {
            if (!password.getText().equals("")) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    startCoins.requestFocus();
                    startCoins.selectAll();
                }
            }
        });

        startCoins.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                if (!startCoins.getText().equals("") && (startCoins.getText()).matches("\\d+") && startCoins.getText() != null) {
                    if (username.getText().equals("")) {
                        username.requestFocus();
                        username.selectAll();
                    } else if (password.getText().equals("")) {
                        password.requestFocus();
                        password.selectAll();
                    } else {
                        on_register();
                    }
                } else {
                    startCoins.requestFocus();
                    startCoins.selectAll();
                }
            }
        });

        startCoins.textProperty().addListener((ov, oldValue, newValue) -> {
            if (!newValue.equals("")) {
                if (!newValue.matches("[0-9]*") || Integer.parseInt(newValue) > 10000) {
                    startCoins.setText(oldValue);
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
        } else if (startCoins.getText() == null) {
            startCoins.requestFocus();
        } else {
            registerModel.register(username.getText(), password.getText(), Integer.parseInt(startCoins.getText()));
        }
    }

    public void btn_back_onAction(ActionEvent actionEvent) {
        registerModel.showStartMenu();
    }
}
