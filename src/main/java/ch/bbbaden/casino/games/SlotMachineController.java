package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.InputStream;

import java.io.File;
import java.sql.SQLException;
import java.util.concurrent.Delayed;

public class SlotMachineController implements Controller {

    public ImageView cherryCollectAction;
    public ImageView threeStarSelection;
    public ImageView betButton;
    public ImageView gambleButton;
    public ImageView mysteryButton;
    public ImageView stopButton;
    public ImageView holdLight;
    public ImageView stepLight;
    public ImageView winLight;
    public ImageView bonusLightMystery;
    public ImageView bonusLight;
    public ImageView TwoFactorLight;
    public ImageView ThreeFactorLight;
    public ImageView FiveFactorLight;
    public ImageView firstFruitRow;
    public ImageView secondFruitRow;
    public ImageView thirdFruitRow;
    public Label coins;
    public Button addCoins;
    public Button plusCoins;
    public Button minusCoins;
    public Label riskLabel;
    public Label gameCoinsLabel;
    public Label addCoinsLabel;
    public Label betFactorLabel;
    public Button receiveWin;
    private SlotMachineModel slotMachineModel;
    private int inputCoins = 0;
    private int gameCoins = 0;
    private int betFactor = 2;


    public void update() {
            spin(slotMachineModel.getRow1(), 1);
            spin(slotMachineModel.getRow2(), 2);
            spin(slotMachineModel.getRow3(), 3);
            coins.setText(Integer.toString(slotMachineModel.getCoins()));
            addCoinsLabel.setText(Integer.toString(inputCoins));
            gameCoinsLabel.setText(Integer.toString(gameCoins));
            betFactorLabel.setText(betFactor + "x");
    }

    private void spin(SlotMachineRow slotMachineRow, int rowNumber) {
       //System.out.println(slotMachineRow.iterator().hasNext());
        if(slotMachineRow.iterator().hasNext()) {

            ImageView row;
            switch (rowNumber) {
                case 2:
                    row = secondFruitRow;
                    break;
                case 3:
                    row = thirdFruitRow;
                    break;
                case 1:
                default:
                    row = firstFruitRow;
                    break;
            }
            PauseTransition pause = new PauseTransition();
            pause.setDuration(Duration.millis(120));
            pause.setOnFinished((ae) -> {
                row.setImage(slotMachineRow.iterator().next().getImage());
                if (slotMachineRow.iterator().hasNext()) {
                    spin(slotMachineRow, rowNumber);
                } else {
                    slotMachineModel.handleSpinResults(slotMachineRow, rowNumber);
                }
            });
            pause.play();
        }
    }

    public void initialize(Model model) {
        slotMachineModel = (SlotMachineModel) model;
        minusCoins.setDisable(true);
        update();
    }

    public void imgV_plusCoins_onMouseClicked() {
        minusCoins.setDisable(false);
        if (slotMachineModel.getCoins() > inputCoins) {
            inputCoins++;
        } else {
            plusCoins.setDisable(true);
        }
        update();
    }

    public void imgV_minusCoins_onMouseClicked() {
        addCoins.setDisable(false);
        if (inputCoins > 0) {
            inputCoins--;
        } else {
            minusCoins.setDisable(true);
        }
        update();
    }

    public void imgV_applyBet_onMouseClicked() {
        slotMachineModel.bet(inputCoins);
        gameCoins += inputCoins;
        inputCoins = 0;
        update();
    }

    public void imgV_stop_onMouseClicked() {
        slotMachineModel.startGame();
    }

    public void imgV_mystery_onMouseClicked() {
        slotMachineModel.mystery();
    }

    public void imgV_gamble_onMouseClicked() {
        slotMachineModel.gamble();
    }

    public void imgV_changeBetFactor_onMouseClicked() {
        slotMachineModel.changeBetFactor();
        update();
    }

    public void imgV_payout_onMouseClicked() {
        slotMachineModel.changeCoins(gameCoins);
        gameCoins = 0;
        update();
    }
}


