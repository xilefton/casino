package ch.bbbaden.casino.games.baccarat;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BaccaratController implements Controller {

    public ImageView imgV_firstCardYou;
    public ImageView imgV_secondCardYou;
    public ImageView imgV_thirdCardYou;
    public ImageView imgV_firstCardCroupier;
    public ImageView imgV_secondCardCroupier;
    public ImageView imgV_thirdCardCroupier;
    public ImageView imgV_firstCroupierCardShield;
    public ImageView imgV_secondCroupierCardShield;
    public ImageView imgV_thirdCroupierCardShield;
    public ComboBox comboBox;
    public Button btn_bet;
    public Button btn_draw;
    public Button btn_newTurn;
    public Button btn_newDeck;
    public Button btn_quit;
    public Label label_coins;
    public Label label_result;
    public Label label_remainingCards;
    public Label label_activeBet;
    public Label label_pointsYou;
    public Label label_pointsCroupier;

    private BaccaratModel baccaratModel;
    private int checkTurn = 1;
    private boolean is5 = false;

    @Override
    public void initialize(Model model) {
        comboBox.getItems().addAll("1", "5", "10", "25", "50", "100");
        comboBox.setValue("100");

        baccaratModel = (BaccaratModel) model;
        baccaratModel.createDeck();
        update();
    }

    public void update() {
        label_coins.setText(Long.toString(baccaratModel.getCoins()));
        label_remainingCards.setText(Integer.toString(baccaratModel.getRemainingCards()));
        label_pointsYou.setText("= " + baccaratModel.getPointsPlayer() + " Punkte");
        label_pointsCroupier.setText("= " + baccaratModel.getPointsCroupier() + " Punkte");
    }

    public void btn_bet_onAction(ActionEvent actionEvent) {
        String selectedBet = comboBox.getValue().toString();
        label_activeBet.setText(selectedBet);
        label_activeBet.setVisible(true);
        if (Long.parseLong(selectedBet) <= baccaratModel.getCoins()) {
            comboBox.setDisable(true);
            btn_bet.setDisable(true);
            btn_newDeck.setDisable(true);
            btn_draw.setDisable(false);
            baccaratModel.changeCoinsBet(Integer.parseInt(selectedBet));
        } else {
            baccaratModel.showMessage("Sie haben zu wenig Geld!", "Nicht möglich");
        }
        update();
    }

    public void btn_draw_onAction(ActionEvent actionEvent) throws InterruptedException {
        switch (checkTurn) {
            case 1:
                baccaratModel.resetPoints();
                showCard(imgV_firstCardYou, false);
                showCard(imgV_firstCardCroupier, false);
                imgV_firstCroupierCardShield.setVisible(true);
                showCard(imgV_secondCardYou, false);
                showCard(imgV_secondCardCroupier, false);
                imgV_secondCroupierCardShield.setVisible(true);
                label_pointsYou.setVisible(true);
                if (baccaratModel.check89() | !baccaratModel.checkPlayerThird()) {
                    manageEnd(true);
                    return;
                }
                if (baccaratModel.check5()) {
                    btn_newTurn.setDisable(false);
                    is5 = true;
                }
                checkTurn++;
                break;
            case 2:
                showCard(imgV_thirdCardYou, true);
                if (!baccaratModel.checkCroupierThird()) {
                    manageEnd(false);
                } else {
                    showCard(imgV_thirdCardCroupier, false);
                    imgV_thirdCroupierCardShield.setVisible(true);
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
        checkTurn = 1;
        btn_draw.setDisable(true);
        btn_newTurn.setDisable(false);
        label_result.setVisible(true);
        imgV_firstCroupierCardShield.setVisible(false);
        imgV_secondCroupierCardShield.setVisible(false);
        imgV_thirdCroupierCardShield.setVisible(false);
        label_pointsCroupier.setVisible(true);
        if (baccaratModel.checkWon()) {
            baccaratModel.manageResult(blackjack);
            label_result.setText("Sie haben " + baccaratModel.getResult() + " Coins gewonnen.");
        } else if (baccaratModel.checkDraw()) {
            baccaratModel.manageResult(blackjack);
            label_result.setText("Unentschieden.");
        } else {
            baccaratModel.manageResult(blackjack);
            label_result.setText("Sie haben verloren.");
        }
        update();
    }

    public void btn_newTurn_onAction(ActionEvent actionEvent) {
        if (!is5) {
            btn_newDeck.setDisable(false);
            btn_newTurn.setDisable(true);
            comboBox.setDisable(false);
            label_result.setVisible(false);
            label_activeBet.setVisible(false);
            label_pointsYou.setVisible(false);
            label_pointsCroupier.setVisible(false);
            if (baccaratModel.getRemainingCards() >= 6) {
                btn_bet.setDisable(false);
            }
            imgV_firstCardYou.setImage(new Image("/images/baccarat/borderCard.png"));
            imgV_firstCardCroupier.setImage(new Image("/images/baccarat/borderCard.png"));
            imgV_secondCardYou.setImage(new Image("/images/baccarat/borderCard.png"));
            imgV_secondCardCroupier.setImage(new Image("/images/baccarat/borderCard.png"));
            imgV_thirdCardYou.setImage(new Image("/images/baccarat/borderCard.png"));
            imgV_thirdCardCroupier.setImage(new Image("/images/baccarat/borderCard.png"));
        } else {
            is5 = false;
            manageEnd(false);
        }
        baccaratModel.setCardForPlayer(true);
        update();
    }

    public void btn_newDeck_onAction(ActionEvent actionEvent) {
        btn_bet.setDisable(false);
        btn_newDeck.setDisable(true);
        baccaratModel.createDeck();
        update();
    }

    public void btn_quit_onAction(ActionEvent actionEvent) {
        baccaratModel.quitGame();
    }
}