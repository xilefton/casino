package ch.bbbaden.casino.games.supercherry;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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
    private long inputCoins = 0;
    private long gameCoins = 0;


    public void update() {
        spin(slotMachineModel.getRow1(), 1);
        spin(slotMachineModel.getRow2(), 2);
        spin(slotMachineModel.getRow3(), 3);
        coins.setText(Long.toString(slotMachineModel.getCoins()));
        addCoinsLabel.setText(Long.toString(inputCoins));
        gameCoinsLabel.setText(Long.toString(gameCoins));
        betFactorLabel.setText(slotMachineModel.getBetFactor() + "x");
        riskLabel.setText(Long.toString(slotMachineModel.getWin()));
        if (slotMachineModel.getWin() > 0) {
            mysteryButton.setDisable(false);
            mysteryButton.setImage(new Image("/images/supercherry/buttons/mystery/BUTTON_MYSTERY_ACTIVE.png"));
            gambleButton.setDisable(false);
            gambleButton.setImage(new Image("/images/supercherry/buttons/gamble/BUTTON_GAMBLE_ACTIVE.png"));
        } else {
            mysteryButton.setDisable(true);
            mysteryButton.setImage(new Image("/images/supercherry/buttons/mystery/BUTTON_MYSTERY_INACTIVE.png"));
            gambleButton.setDisable(true);
            gambleButton.setImage(new Image("/images/supercherry/buttons/gamble/BUTTON_GAMBLE_INACTIVE.png"));
        }
    }

    private void spin(SlotMachineRow slotMachineRow, int rowNumber) {
        if (slotMachineRow.iterator().hasNext()) {
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
                    if (slotMachineModel.getCherryCollect()) {
                        slotMachineModel.countCherry(slotMachineRow, rowNumber);
                    } else
                        slotMachineModel.handleSpinResults(slotMachineRow, rowNumber);
                }
            });
            pause.play();
        }
    }

    public void initialize(Model model) {
        stopButton.setDisable(true);
        stopButton.setImage(new Image("/images/supercherry/buttons/stop/BUTTON_STOP_INACTIVE.png"));
        betButton.setDisable(true);
        betButton.setImage(new Image("/images/supercherry/buttons/bet/BUTTON_BET_INACTIVE.png"));
        mysteryButton.setDisable(true);
        mysteryButton.setImage(new Image("/images/supercherry/buttons/mystery/BUTTON_MYSTERY_INACTIVE.png"));
        gambleButton.setDisable(true);
        gambleButton.setImage(new Image("/images/supercherry/buttons/gamble/BUTTON_GAMBLE_INACTIVE.png"));
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
        stopButton.setDisable(false);
        stopButton.setImage(new Image("/images/supercherry/buttons/stop/BUTTON_STOP_ACTIVE.png"));
        betButton.setDisable(false);
        betButton.setImage(new Image("/images/supercherry/buttons/bet/BUTTON_BET_ACTIVE.png"));
        slotMachineModel.bet(inputCoins);
        gameCoins += inputCoins;
        inputCoins = 0;
        update();
    }

    public void imgV_stop_onMouseClicked() {
        if (gameCoins >= 2 && gameCoins >= slotMachineModel.getBetFactor()) {
            slotMachineModel.startGame();
            int usedGameCoins = slotMachineModel.getBetFactor();
            slotMachineModel.setUsedGameCoins(usedGameCoins);
            gameCoins = gameCoins - usedGameCoins;
        }
        if (slotMachineModel.getWin() > 0) {
            gameCoins = gameCoins + slotMachineModel.getWin();
            slotMachineModel.resetWin();
        }
    }

    public void imgV_mystery_onMouseClicked() {
        slotMachineModel.mystery();
    }

    public void imgV_gamble_onMouseClicked() {
        slotMachineModel.gamble();
    }

    public void imgV_changeBetFactor_onMouseClicked() {
        slotMachineModel.changeBetFactor();
    }

    public void imgV_payout_onMouseClicked() {
        slotMachineModel.playerWin(gameCoins);
        gameCoins = 0;
        update();
    }

    public void btn_quit_onAction() {
        slotMachineModel.quit();
    }
}


