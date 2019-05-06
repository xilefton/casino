package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    public static Label firstRowLabel;
    public static Label secondRowLabel;
    public static Label thirdRowLabel;
    public Label betFactorLabel;
    public Label betCoinsLabel;
    private SlotMachineModel slotMachineModel;
    private int inputCoins = 0;
    private  int gameCoins = 0;


    public void update() {
        coins.setText(slotMachineModel.getCoins());
        addCoinsLabel.setText(Integer.toString(inputCoins));
        gameCoinsLabel.setText(Integer.toString(gameCoins));
        betFactorLabel.setText(SlotMachineModel.getBetFactor() + "x");
        betCoinsLabel.setText((Integer.toString(SlotMachineModel.getBetCoins())));
        riskLabel.setText(Integer.toString(SlotMachineModel.calculateWin()));
    }

    public void initialize(Model model) {
        slotMachineModel = (SlotMachineModel) model;
        update();
        stopButton.setDisable(true);
        mysteryButton.setDisable(true);
        betButton.setDisable(true);
        gambleButton.setDisable(true);
        stopButton.setImage(SlotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/stop/BUTTON_STOP_INACTIVE.png"));
        mysteryButton.setImage(SlotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/mystery/BUTTON_MYSTERY_INACTIVE.png"));
        betButton.setImage(SlotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/bet/BUTTON_BET_INACTIVE.png"));
        gambleButton.setImage(SlotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/gamble/BUTTON_GAMBLE_INACTIVE.png"));
        addCoins.setDisable(true);
        minusCoins.setDisable(true);
        plusCoins.setDisable(false);
        firstFruitRow.setVisible(true);
        firstFruitRow.toFront();

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
        betButton.setImage(SlotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/bet/BUTTON_BET_ACTIVE.png"));
        plusCoins.setDisable(false);
        minusCoins.setDisable(true);
        gameCoins += inputCoins;
        if(gameCoins > 1) {
            stopButton.setDisable(false);
            stopButton.setImage(SlotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/stop/BUTTON_STOP_ACTIVE.png"));
        }
        inputCoins = 0;
        update();
    }
    public void stopButton(MouseEvent mouseEvent) {
        addCoins.setDisable(true);
        gambleButton.setDisable(false);
        gambleButton.setImage(SlotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/gamble/BUTTON_GAMBLE_ACTIVE.png"));
        mysteryButton.setDisable(false);
        mysteryButton.setImage(SlotMachineModel.setButtonImage("src/main/resources/images/supercherry/buttons/mystery/BUTTON_MYSTERY_ACTIVE.png"));
        stopButton.setDisable(false);
        betButton.setDisable(false);
        plusCoins.setDisable(true);
        minusCoins.setDisable(true);
        SlotMachineModel.spinFruits(firstFruitRow,secondFruitRow,thirdFruitRow);
    }
    public void mysteryButton(MouseEvent mouseEvent) { SlotMachineModel.mystery(); update();}
    public void gambleButton(MouseEvent mouseEvent) { SlotMachineModel.gamble(); update();}
    public void betButton(MouseEvent mouseEvent) { SlotMachineModel.bet(); update();}
}


