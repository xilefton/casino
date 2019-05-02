package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class BaccaratController implements Controller {
    public Label coins;
    public Button bet;
    public Button end;
    public Button draw;
    public ComboBox comboBox;

    private BaccaratModel baccaratModel;

    public void update(Model model) {
        baccaratModel = (BaccaratModel) model;
    }

    public void update() {
        coins.setText(baccaratModel.getCoins());
    }

    public void initialize(Model model) {
        baccaratModel = (BaccaratModel) model;
        comboBox.getItems().addAll("5", "10", "25", "50", "100");
        comboBox.setValue("5");
        update();
    }

    public void bet(ActionEvent actionEvent) throws SQLException {
        comboBox.setDisable(true);
        bet.setDisable(true);
        end.setDisable(false);
        draw.setDisable(false);
        String selected = comboBox.getValue().toString();
        baccaratModel.updateCoins(-Integer.parseInt(selected), false);
        update();
    }

    public void draw(ActionEvent actionEvent) {
    }

    public void end(ActionEvent actionEvent) {
    }
}