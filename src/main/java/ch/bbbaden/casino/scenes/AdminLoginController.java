package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class AdminLoginController implements Controller {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private AdminLoginModel adminLoginModel;

    @Override
    public void initialize(Model model) {
        adminLoginModel = (AdminLoginModel) model;
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
                    adminLoginModel.login(username.getText(), password.getText());
                }
            }
        });
    }

    @Override
    public void update() {
        username.setText(adminLoginModel.getUsername());
        password.setText(adminLoginModel.getPassword());
    }

    public void on_login(ActionEvent actionEvent) {
        adminLoginModel.login(username.getText(), password.getText());
    }

    public void btn_back_onAction(ActionEvent actionEvent) {
        adminLoginModel.showStartMenu();
    }

}
