package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.sql.SQLException;

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
    public HBox hBoxScrollBars;
    public Label coins;
    public Button addCoins;
    public Button plusCoins;
    public Button minusCoins;
    public Label riskLabel;
    public Label winLabel;
    public Label addCoinsLabel;
    private SlotMachineModel slotMachineModel;
    private int inputCoins = 0;
    private  int gameCoins;

    public SlotMachineController(int gameCoins) {
        this.gameCoins = gameCoins;
    }

    public void update() {
        coins.setText(slotMachineModel.getCoins()); 
    }
    private void updateInputCoins() { addCoinsLabel.setText(Integer.toString(inputCoins)); }
    private  void updateGameCoins() {winLabel.setText(Integer.toString(gameCoins));}

    public void initialize(Model model) {
        stopButton.setDisable(true);
        addCoins.setDisable(true);
        betButton.setDisable(true);
        gambleButton.setDisable(true);
        mysteryButton.setDisable(true);
        minusCoins.setDisable(true);
        plusCoins.setDisable(false);
        slotMachineModel = (SlotMachineModel) model;
        update();
        Image fruit1 = new Image(getClass().getResourceAsStream("../images/supercherry/BELL.png"));
        firstFruitRow = new ImageView(fruit1);
        Image fruit2 = new Image(getClass().getResourceAsStream("../images/supercherry/LEMON.png"));
        firstFruitRow = new ImageView(fruit2);
        Image fruit3 = new Image(getClass().getResourceAsStream("../images/supercherry/MELON.png"));
        firstFruitRow = new ImageView(fruit3);
    }
    public void plusCoins(MouseEvent mouseEvent) throws SQLException {
        minusCoins.setDisable(false);
        addCoins.setDisable(false);
        if (Integer.parseInt(slotMachineModel.getCoins()) >= 0) {
            inputCoins += 1;
            int coins = Integer.parseInt(slotMachineModel.getCoins()) - 1;
            slotMachineModel.updateCoins(coins, false);
            updateInputCoins();
            update();
        } else {
           plusCoins.setDisable(true);
        }
    }
    public void minusCoins(MouseEvent mouseEvent) throws SQLException {
        if (inputCoins >= 0) {
            inputCoins -= 1;
            int coins = Integer.parseInt(slotMachineModel.getCoins()) + 1;
            slotMachineModel.updateCoins(coins, false);
            updateInputCoins();
            update();
        } else {
            minusCoins.setDisable(true);
        }
    }
    public void addCoins(MouseEvent mouseEvent) {
        addCoins.setDisable(true);
        gambleButton.setDisable(true);
        mysteryButton.setDisable(true);
        stopButton.setDisable(false);
        betButton.setDisable(false);
        plusCoins.setDisable(false);
        minusCoins.setDisable(true);
        gameCoins = inputCoins;
        inputCoins = 0;
        updateInputCoins();
        updateGameCoins();
        update();
    }
    public void stopButton(MouseEvent mouseEvent) {
       slotMachineModel.play();
    }
    public void mysteryButton(MouseEvent mouseEvent) { }
    public void gambleButton(MouseEvent mouseEvent) { }
    public void betButton(MouseEvent mouseEvent) { }
}

