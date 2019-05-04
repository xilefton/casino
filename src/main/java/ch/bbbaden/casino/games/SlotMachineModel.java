package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;


public class SlotMachineModel extends Game {

    private NormalUser normalUser;

    public SlotMachineModel(NormalUser normalUser) {
        super("/fxml/SlotMachine.fxml", "Super Cherry", "/images/SuperCherry_Logo.png", normalUser);
        this.normalUser = normalUser;

    }

    public String getCoins() {
        try {
            return Integer.toString(normalUser.getCoins());
        } catch (SQLException e) {
            System.err.println(e);
        } return null;
    }
    public void updateCoins(int coins, boolean purchased) throws SQLException {
        normalUser.addCoins(coins, purchased);
    }
    public void play() {
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
            /*hBoxScrollBars.getChildren().add(pics[i]);
            firstScrollPane.setContent(hBoxScrollBars);
            secondScrollPane.setContent(hBoxScrollBars);
            thirdScrollPane.setContent(hBoxScrollBars);*/
        }
    }
}
