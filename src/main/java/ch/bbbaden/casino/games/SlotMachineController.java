package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.animation.PauseTransition;
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

    public AnchorPane anchorPaneSuperCherry;
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
    public Label betCoinsLabel;
    public Button receiveWin;
    private SlotMachineModel slotMachineModel;
    private SlotMachineRow slotMachineRow;
    private int inputCoins = 0;
    private int gameCoins = 0;
    private int usedGameCoins = 0;


    public void update() {
        coins.setText(slotMachineModel.getCoins());
        addCoinsLabel.setText(Integer.toString(inputCoins));
        gameCoinsLabel.setText(Integer.toString(gameCoins));
        betFactorLabel.setText(slotMachineModel.getBetFactor() + "x");
        betCoinsLabel.setText((Integer.toString(slotMachineModel.getBetCoins())));
        threeStarSelection.setImage(slotMachineModel.setButtonImage(slotMachineModel.getUrlOfThreeStarWinImage()));
        int winCoins = usedGameCoins * slotMachineModel.getWinFactor();
        if(slotMachineModel.getWin()) {
            riskLabel.setText("0 - " + winCoins + " - " + winCoins * 2);
        } else {
            riskLabel.setText("0");
        }
    }
    public void initialize(Model model) {
        slotMachineModel = (SlotMachineModel) model;
        stopButton.setDisable(true);
        mysteryButton.setDisable(true);
        betButton.setDisable(true);
        gambleButton.setDisable(true);
        stopButton.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/stop/BUTTON_STOP_INACTIVE.png"));
        mysteryButton.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/mystery/BUTTON_MYSTERY_INACTIVE.png"));
        betButton.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/bet/BUTTON_BET_INACTIVE.png"));
        gambleButton.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/gamble/BUTTON_GAMBLE_INACTIVE.png"));
        threeStarSelection.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/threestarwin/3-STAR-SELECTION.png"));
        addCoins.setDisable(true);
        minusCoins.setDisable(true);
        plusCoins.setDisable(false);
        firstFruitRow.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/fruits/STAR.png"));
        secondFruitRow.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/fruits/STAR.png"));
        thirdFruitRow.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/fruits/STAR.png"));
        update();
    }
    public void plusCoins(MouseEvent mouseEvent) {
        minusCoins.setDisable(false);
        addCoins.setDisable(false);
        if (Integer.parseInt(slotMachineModel.getCoins()) >= 1) {
            inputCoins += 1;
            slotMachineModel.updateCoins(-1, false);
            update();
        } else {
           plusCoins.setDisable(true);
           update();
        }
    }
    public void minusCoins(MouseEvent mouseEvent) throws SQLException {
        if (inputCoins >= 1) {
            inputCoins -= 1;
            slotMachineModel.updateCoins(+1, false);
            plusCoins.setDisable(false);
            update();
        } else {
            minusCoins.setDisable(true);
        }
    }
    public void addCoins(MouseEvent mouseEvent) {
        addCoins.setDisable(true);
        gambleButton.setDisable(true);
        mysteryButton.setDisable(true);
        betButton.setDisable(false);
        betButton.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/bet/BUTTON_BET_ACTIVE.png"));
        plusCoins.setDisable(false);
        minusCoins.setDisable(true);
        gameCoins += inputCoins;
        if(gameCoins > 1) {
            stopButton.setDisable(false);
            stopButton.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/stop/BUTTON_STOP_ACTIVE.png"));
        }
        inputCoins = 0;
        update();
    }
    public void stopButton(MouseEvent mouseEvent) {
        addCoins.setDisable(true);
        plusCoins.setDisable(true);
        minusCoins.setDisable(true);
        betButton.setDisable(true);
        betButton.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/bet/BUTTON_BET_INACTIVE.png"));
        stopButton.setDisable(true);
        stopButton.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/stop/BUTTON_STOP_INACTIVE.png"));
        if(gameCoins >= 2) {
            usedGameCoins = slotMachineModel.getBetFactor();
            gameCoins = gameCoins - usedGameCoins;
            slotMachineModel.spinFruits(firstFruitRow, secondFruitRow, thirdFruitRow);
            update();
            PauseTransition transition = new PauseTransition(Duration.seconds(5));
            transition.setOnFinished(event -> {
                slotMachineModel.stopSpinning();
                gambleButton.setDisable(false);
                gambleButton.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/gamble/BUTTON_GAMBLE_ACTIVE.png"));
                mysteryButton.setDisable(false);
                mysteryButton.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/mystery/BUTTON_MYSTERY_ACTIVE.png"));
                stopButton.setDisable(false);
                stopButton.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/stop/BUTTON_STOP_ACTIVE.png"));
                betButton.setDisable(false);
                betButton.setImage(slotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/bet/BUTTON_BET_ACTIVE.png"));
                plusCoins.setDisable(false);
                switch (slotMachineModel.getWinFactor()) {
                    case 1:
                        slotMachineModel.hold(thirdFruitRow, 3);
                        break;
                    case 6:
                        slotMachineModel.step(thirdFruitRow, 3);
                        break;
                    case  7:
                        slotMachineModel.step(firstFruitRow, 1);
                        break;
                    case 8:
                        slotMachineModel.step(secondFruitRow, 2);
                        break;
                    case 9:
                        slotMachineModel.hold(firstFruitRow, 1);
                        break;
                    case 11:
                        slotMachineModel.hold(secondFruitRow, 2);
                        default:
                            usedGameCoins = usedGameCoins * slotMachineModel.getWinFactor();
                            gameCoins += usedGameCoins;
                            usedGameCoins = 0;
                }
                update();
            });
            transition.play();
        } else {
            plusCoins.setDisable(false);
        }
    }
    public void mysteryButton(MouseEvent mouseEvent) { slotMachineModel.mystery(); update();}
    public void gambleButton(MouseEvent mouseEvent) { slotMachineModel.gamble(); update();}
    public void betButton(MouseEvent mouseEvent) { slotMachineModel.bet(gameCoins); update();}
    public void receiveWin(MouseEvent mouseEvent) {
        slotMachineModel.updateCoins(gameCoins, false);
        gameCoins = 0;
        update();}
}


