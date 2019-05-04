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

import java.awt.event.ActionEvent;
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
    public ScrollPane firstScrollPane;
    public ScrollPane secondScrollPane;
    public ScrollPane thirdScrollPane;
    public HBox hBoxScrollBars;
    public Label coins;
    public Button addCoins;
    public Button plusCoins;
    public Button minusCoins;
    public Label riskLabel;
    public Label winLabel;
    public Label addCoinsLabel;
    private SlotMachineModel slotMachineModel;

    public void update() {
        coins.setText(slotMachineModel.getCoins()); 
    }

    public void initialize(Model model) {
        slotMachineModel = (SlotMachineModel) model;
        update();
    }
    public void onClick(MouseEvent mouseEvent) {
        getFruitsView();
    }
    public void plusCoins(MouseEvent mouseEvent) throws SQLException { slotMachineModel.addCoins();
    }


    private void getFruitsView() {
        Image[] images = new Image[8];
        ImageView[] pics = new ImageView[8];
        final String[] imageNames = new String[]{"../images/supercherry/fruits/BELL.png", "../images/supercherry/fruits/CHERRY.png",
                "../images/supercherry/fruits/GRAPES.png", "../images/supercherry/fruits/LEMON.png", "../images/supercherry/fruits/MELON.png",
                "../images/supercherry/fruits/PEACH.png", "../images/supercherry/fruits/POTATO.png", "../images/supercherry/fruits/STAR.png",
                "../images/supercherry/fruits/STRAWBERRY.png"};

        for (int i = 0; i < 8; i++) {
            images[i] = new Image(getClass().getResourceAsStream(imageNames[i]));
            pics[i] = new ImageView(images[i]);
            pics[i].setFitWidth(100);
            pics[i].setPreserveRatio(true);
            hBoxScrollBars.getChildren().add(pics[i]);
            firstScrollPane.setContent(hBoxScrollBars);
            secondScrollPane.setContent(hBoxScrollBars);
            thirdScrollPane.setContent(hBoxScrollBars);

        }
    }
    public void addCoins() {
    }



}

