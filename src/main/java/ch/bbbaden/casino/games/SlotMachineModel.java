package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;


public class SlotMachineModel extends Game {

    private NormalUser normalUser;
    private static  SlotMachineRow firstRow, secondRow, thirdRow;
    private static String urlOfFirstImage, urlOfSecondImage, urlOfThirdImage;
    private static Thread firstThread, secondThread, thirdThread;
    private static int winFactor;
    private static String threeStarWin;
    private static boolean win = false;
    private static String betFactor;
    private static int bet = 1;
    private static int betCoins;
    private static Image buttonImage;

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
    private static void calculateWin() {
        if(urlOfFirstImage.equals(urlOfSecondImage) && urlOfFirstImage.equals((urlOfThirdImage))) {
            switch (urlOfFirstImage) {
                case "/src/main/resources/images/supercherry/fruits/STAR.png":
                    String selectWin = threeStarWin();
                    switch (selectWin) {
                        case "FruitStop":
                            winFactor = 100;
                            break;
                        case "2xShuffle":
                            winFactor = 101;
                        case "4xShuffle":
                            winFactor = 102;
                            break;
                        case "10x":
                            winFactor = 103;
                        case "CherryCollect":
                            winFactor = 104;
                            break;
                    }
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
    }
    public static void gamble() {
        int randomNumber = (int) (Math.random() * 2);
        if(randomNumber == 1) {
            winFactor = 4;
        } else {
            winFactor = 0;
        }
    }
    public static void bet() {
        if(win) {
            betCoins++;
        } else {
            switch (bet) {
                case 1:
                    bet++;
                    betFactor = "1x";
                    break;
                case 2:
                    bet++;
                    betFactor = "2x";
                    break;
                case 3:
                    bet++;
                    betFactor = "5x";
                    break;
                case 4:
                    bet++;
                    betFactor = "10x";
                    break;
                case 5:
                    bet++;
                    betFactor = "20x";
                    break;
                case 6:
                    bet = 0;
                    betFactor = "50x";
                    break;
            }
        }
    }
    public static String getBetFactor() {return betFactor;}
    public static int getBetCoins() {return betCoins;}
    public static int getWinFactor() {return winFactor;}

    public static void mystery() {
        int rN = (int) (Math.random() * 2);
        int randomNumber = (int) (Math.random() * 30);
        if(rN == 1) {
            if (randomNumber <= 15) {
            winFactor = 2;
            } else if (randomNumber <= 25) {
                winFactor = 3;
            } else if (randomNumber <= 30) {
                winFactor = 5;
            }
        } else {
            winFactor = 0;
        }
    }

    public static String threeStarWin() {
        int randomNumber = (int) (Math.random() * 5);
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
    public static Image setButtonImage(String path) {
        File file = new File(path);
        try {
            buttonImage = new Image(file.toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return buttonImage;
    }
}
