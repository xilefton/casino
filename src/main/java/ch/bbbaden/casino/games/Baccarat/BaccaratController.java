package ch.bbbaden.casino.games.Baccarat;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class BaccaratController implements Controller {
    public Label coins;
    public Button bet;
    public Button end;
    public Button draw;
    public ComboBox comboBox;
    public ImageView firstYou;
    public ImageView secondYou;
    public ImageView thirdYou;
    public ImageView firstCroupier;
    public ImageView secondCroupier;
    public ImageView thirdCroupier;
    public Button newDeck;
    public Label result;

    private BaccaratModel baccaratModel;
    int i = 0;

    public void update() {
        coins.setText(baccaratModel.getCoins());
    }

    public void initialize(Model model) {
        comboBox.getItems().addAll("5", "10", "25", "50", "100");
        comboBox.setValue("5");

        baccaratModel = (BaccaratModel) model;
        baccaratModel.createDeck();
        update();
    }

    public void bet(ActionEvent actionEvent) {
        comboBox.setDisable(true);
        bet.setDisable(true);
        draw.setDisable(false);
        end.setDisable(false);
        String selectedBet = comboBox.getValue().toString();
        try {
            baccaratModel.updateCoins(-Integer.parseInt(selectedBet), false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        update();
    }

    public void draw(ActionEvent actionEvent) {
        switch (i) {
            case 0:
                firstYou.setImage(new Image(baccaratModel.getRandomCard()));
                i++;
                break;
            case 1:
                firstCroupier.setImage(new Image(baccaratModel.getRandomCard()));
                i++;
                break;
            case 2:
                secondYou.setImage(new Image(baccaratModel.getRandomCard()));
                i++;
                break;
            case 3:
                secondCroupier.setImage(new Image(baccaratModel.getRandomCard()));
                i++;
                break;
            case 4:
                thirdYou.setImage(new Image(baccaratModel.getRandomCard()));
                i++;
                break;
            case 5:
                thirdCroupier.setImage(new Image(baccaratModel.getRandomCard()));
                draw.setDisable(true);
                break;
        }
    }

    public void endTurn(ActionEvent actionEvent) {
        newDeck.setDisable(false);
    }

    public void newDeck(ActionEvent actionEvent) {
        baccaratModel.createDeck();
    }
}