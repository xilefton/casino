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

    public ImageView firstYou;
    public ImageView secondYou;
    public ImageView thirdYou;
    public ImageView firstCroupier;
    public ImageView secondCroupier;
    public ImageView thirdCroupier;
    public ImageView firstCroupierShield;
    public ImageView secondCroupierShield;
    public ImageView thirdCroupierShield;
    public ComboBox comboBox;
    public Button bet;
    public Button draw;
    public Button newTurn;
    public Button newDeck;
    public Label coins;
    public Label result;
    public Label remainingCards;
    public Label activatedBet;

    private BaccaratModel baccaratModel;
    private int i = 1;
    private boolean is5 = false;

    public void update() {
        coins.setText(baccaratModel.getCoins());
        remainingCards.setText(Integer.toString(baccaratModel.getRemainingCards()));
    }

    @Override
    public void initialize(Model model) {
        comboBox.getItems().addAll("5", "10", "25", "50", "100");
        comboBox.setValue("100");

        baccaratModel = (BaccaratModel) model;
        baccaratModel.createDeck();
        update();
    }

    public void bet(ActionEvent actionEvent) {
        String selectedBet = comboBox.getValue().toString();
        activatedBet.setText(selectedBet);
        activatedBet.setVisible(true);
        if (Integer.parseInt(selectedBet) <= Integer.parseInt(baccaratModel.getCoins())) {
            comboBox.setDisable(true);
            bet.setDisable(true);
            newDeck.setDisable(true);
            draw.setDisable(false);
            try {
                baccaratModel.updateCoins(Integer.parseInt(selectedBet), false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //TO-DO
            //pop-up error
        }
        update();
    }

    public void draw(ActionEvent actionEvent) throws InterruptedException {
        switch (i) {
            case 1:
                showCard(firstYou, false);
                showCard(firstCroupier, false);
                firstCroupierShield.setVisible(true);
                showCard(secondYou, false);
                showCard(secondCroupier, false);
                secondCroupierShield.setVisible(true);
                if (baccaratModel.check89() | !baccaratModel.checkPlayerThird()) {
                    manageEnd(true);
                    return;
                }
                if (baccaratModel.check5()) {
                    newTurn.setDisable(false);
                    is5 = true;
                }
                i++;
                break;
            case 2:
                showCard(thirdYou, true);
                if (!baccaratModel.checkCroupierThird()) {
                    manageEnd(false);
                } else {
                    showCard(thirdCroupier, false);
                    thirdCroupierShield.setVisible(true);
                    manageEnd(false);
                }
                is5 = false;
                break;
        }
        update();
    }

    public void showCard(ImageView imageView, boolean thirdPlayerCard) {
        imageView.setImage(new Image(baccaratModel.getRandomCard(thirdPlayerCard)));
    }

    public void manageEnd(boolean blackjack) {
        i = 1;
        draw.setDisable(true);
        newTurn.setDisable(false);
        result.setVisible(true);
        firstCroupierShield.setVisible(false);
        secondCroupierShield.setVisible(false);
        thirdCroupierShield.setVisible(false);
        if (baccaratModel.checkWon()) {
            baccaratModel.manageResult(blackjack);
            result.setText("Sie haben " + baccaratModel.getResult() + " Coins gewonnen.");
        } else if (baccaratModel.checkDraw()) {
            baccaratModel.manageResult(blackjack);
            result.setText("Unentschieden.");
        } else {
            baccaratModel.manageResult(blackjack);
            result.setText("Sie haben verloren.");
        }
        update();
    }

    public void newTurn(ActionEvent actionEvent) {
        if (!is5) {
            newDeck.setDisable(false);
            newTurn.setDisable(true);
            comboBox.setDisable(false);
            result.setVisible(false);
            activatedBet.setVisible(false);
            if (baccaratModel.getRemainingCards() >= 6) {
                bet.setDisable(false);
            }
            firstYou.setImage(new Image("/images/baccarat/borderCard.png"));
            firstCroupier.setImage(new Image("/images/baccarat/borderCard.png"));
            secondYou.setImage(new Image("/images/baccarat/borderCard.png"));
            secondCroupier.setImage(new Image("/images/baccarat/borderCard.png"));
            thirdYou.setImage(new Image("/images/baccarat/borderCard.png"));
            thirdCroupier.setImage(new Image("/images/baccarat/borderCard.png"));
        } else {
            is5 = false;
            manageEnd(false);
        }
        baccaratModel.setForPlayer(true);
        update();
    }

    public void newDeck(ActionEvent actionEvent) {
        bet.setDisable(false);
        newDeck.setDisable(true);
        baccaratModel.createDeck();
        update();
    }
}