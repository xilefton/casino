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
    private static int winFactor;
    private int randomNumber;
    private String threeStarWin;
    private int mysteryFactor;

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
        calculateWin();
    }
    public static int calculateWin() {
        if(urlOfFirstImage.equals(urlOfSecondImage) && urlOfFirstImage.equals((urlOfThirdImage))) {
            switch (urlOfFirstImage) {
                case "/src/main/resources/images/supercherry/fruits/STAR.png":
                    winFactor = 100;
                    break;
                case "/src/main/resources/images/supercherry/fruits/BELL.png":
                    winFactor = 50;
                    break;
                case "/src/main/resources/images/supercherry/fruits/CHERRY.png":
                    winFactor = 20;
                    break;
                case "/src/main/resources/images/supercherry/fruits/PEACH.png":
                case "/src/main/resources/images/supercherry/fruits/MELON.png":
                    winFactor = 10;
                    break;
                case "/src/main/resources/images/supercherry/fruits/STRAWBERRY.png":
                case "/src/main/resources/images/supercherry/fruits/LEMON.png":
                    winFactor = 5;
                    break;
                case "/src/main/resources/images/supercherry/fruits/POTATO.png":
                case "/src/main/resources/images/supercherry/fruits/GRAPES.png":
                    winFactor = 2;
                    break;
            }
        }
        else if (urlOfFirstImage.equals(urlOfSecondImage) || urlOfSecondImage.equals(urlOfThirdImage) || urlOfFirstImage.equals(urlOfThirdImage)) {
            if(urlOfFirstImage.equals("/src/main/resources/images/supercherry/fruits/CHERRY.png") && urlOfSecondImage.equals("/src/main/resources/images/supercherry/fruits/CHERRY.png") ||
                    urlOfSecondImage.equals("/src/main/resources/images/supercherry/fruits/CHERRY.png") && urlOfThirdImage.equals("/src/main/resources/images/supercherry/fruits/CHERRY.png") ||
                    urlOfFirstImage.equals("/src/main/resources/images/supercherry/fruits/CHERRY.png") && urlOfThirdImage.equals("/src/main/resources/images/supercherry/fruits/CHERRY.png")) {
                winFactor = 3;
            } else {
                winFactor = 1;
            }
        } else winFactor = 0;
        return winFactor;
    }
    public void gamble() {
        randomNumber = (int) (Math.random() * 2);
        if(randomNumber == 1) {
            winFactor = 4;
        } else {
            winFactor = 0;
        }
    }
    public int mystery() {
        randomNumber = (int) (Math.random() * 2);
        if(randomNumber == 1) {
            randomNumber = (int) (Math.random() * 30);
            if (randomNumber <= 15) {
            mysteryFactor = 2;
            } else if (randomNumber >= 16 && randomNumber <= 25) {
                mysteryFactor = 3;
            } else if (randomNumber >= 26) {
                mysteryFactor = 5;
            }
        } else {
            mysteryFactor = 0;
        } return mysteryFactor;
    }
    public void bet(Boolean win) {
        if(win) {

        } else {

        }
    }
    public String threeStarWin() {
        randomNumber = (int) (Math.random() * 5);
        switch (randomNumber) {
            case 1:
                threeStarWin = "FruitStop";
                break;
            case 2:
                threeStarWin = "2xShuffle";
                break;
            case 3:
                threeStarWin = "4xShuffle";
                break;
            case 4:
                threeStarWin = "10x";
                break;
            case 5:
                threeStarWin = "CherryCollect";
                break;
        } return threeStarWin;
    }
}
