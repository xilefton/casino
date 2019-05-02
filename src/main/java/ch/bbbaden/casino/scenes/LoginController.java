package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class LoginController implements Controller {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private LoginModel loginModel;

    public void initialize(Model model) {
        loginModel = (LoginModel) model;
        username.textProperty().addListener((ov, oldValue, newValue) -> username.setText(newValue.toUpperCase()));
        username.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                password.requestFocus();
                password.selectAll();
            }
        });
        password.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                if (username.getText().equals("")) {
                    username.requestFocus();
                } else {
                    loginModel.login(username.getText(), password.getText());
                }
            }
        });
    }

    public void update() {
        username.setText(loginModel.getUsername());
        password.setText(loginModel.getPassword());
    }

    @FXML
    private void on_login() {
        loginModel.login(username.getText(), password.getText());
    }

    @FXML
    public void btn_back_onAction() {
        loginModel.showStartMenu();
    }
}