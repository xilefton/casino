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
    public void updateCoins(int coins, boolean purchased)  {
        try {
            normalUser.addCoins(coins, purchased);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void spinFruits() {

        SlotMachineRow firstRow = new SlotMachineRow(SlotMachineController.firstRowLabel);
        Thread firstThread = new Thread(firstRow);
        firstThread.start();

        SlotMachineRow secondRow = new SlotMachineRow(SlotMachineController.secondRowLabel);
        Thread secondThread = new Thread(secondRow);
        secondThread.start();

        SlotMachineRow thirdRow = new SlotMachineRow(SlotMachineController.thirdRowLabel);
        Thread thirdThread = new Thread(thirdRow);
        thirdThread.start();

    }
}
