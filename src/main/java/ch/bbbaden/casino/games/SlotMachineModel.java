package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;


public class SlotMachineModel extends Game {

    private NormalUser normalUser;
    private static  SlotMachineRow firstRow, secondRow, thirdRow;
    private static String urlOfFirstImage, urlOfSecondImage, urlOfThirdImage;
    private static Thread firstThread, secondThread, thirdThread;

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

        firstRow = new SlotMachineRow(SlotMachineController.firstFruitRow);
        firstThread = new Thread(firstRow);
        firstThread.start();

        secondRow = new SlotMachineRow(SlotMachineController.secondFruitRow);
        secondThread = new Thread(secondRow);
        secondThread.start();

        thirdRow = new SlotMachineRow(SlotMachineController.thirdFruitRow);
        thirdThread = new Thread(thirdRow);
        thirdThread.start();

    }
    public static void stopSpinning() {
        firstThread.stop();
        secondThread.stop();
        thirdThread.stop();
        urlOfFirstImage=firstRow.getUrlOfImage();
        urlOfSecondImage=secondRow.getUrlOfImage();
        urlOfThirdImage=thirdRow.getUrlOfImage();
    }
}
