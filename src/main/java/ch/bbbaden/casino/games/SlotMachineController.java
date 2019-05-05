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
    public static ImageView firstFruitRow;
    public static ImageView secondFruitRow;
    public static ImageView thirdFruitRow;
    public Label coins;
    public Button addCoins;
    public Button plusCoins;
    public Button minusCoins;
    public Label riskLabel;
    public Label winLabel;
    public Label addCoinsLabel;
    public static Label firstRowLabel;
    public static Label secondRowLabel;
    public static Label thirdRowLabel;
    public ImageView firstFruitRowImage;
    public ImageView secondFruitRowImage;
    public ImageView thirdFruitRowImage;
    private SlotMachineModel slotMachineModel;
    private int inputCoins = 0;
    private  int gameCoins;

    public void update() {
        coins.setText(slotMachineModel.getCoins());
        addCoinsLabel.setText(Integer.toString(inputCoins));
        winLabel.setText(Integer.toString(gameCoins));
    }

    public void initialize(Model model) {
        stopButton.setDisable(true);
        addCoins.setDisable(true);
        betButton.setDisable(true);
        gambleButton.setDisable(true);
        mysteryButton.setDisable(true);
        minusCoins.setDisable(true);
        plusCoins.setDisable(false);
        slotMachineModel = (SlotMachineModel) model;

        String urlOfImage = SlotMachineRow.getRandomFruit();
        File file = new File(urlOfImage);
        Image image = new Image(file.toURI().toString());
        thirdFruitRowImage.setImage(image);
        System.out.println(urlOfImage);
        update();

        /*File file = new File("src/main/resources/images/supercherry/fruits/BELL.png");
        Image image = new Image(file.toURI().toString());
        firstFruitRowImage.setImage(image);*/

    }


    public void plusCoins(MouseEvent mouseEvent) throws SQLException {
        minusCoins.setDisable(false);
        addCoins.setDisable(false);
        if (Integer.parseInt(slotMachineModel.getCoins()) >= 0) {
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
        update();
    }
    public void stopButton(MouseEvent mouseEvent) {
        addCoins.setDisable(true);
        gambleButton.setDisable(true);
        mysteryButton.setDisable(false);
        stopButton.setDisable(true);
        betButton.setDisable(true);
        plusCoins.setDisable(true);
        minusCoins.setDisable(true);
        SlotMachineModel.spinFruits();
        System.out.println(SlotMachineModel.calculateWin());
    }
    public void mysteryButton(MouseEvent mouseEvent) { SlotMachineModel.stopSpinning();}
    public void gambleButton(MouseEvent mouseEvent) { }
    public void betButton(MouseEvent mouseEvent) { }
}

