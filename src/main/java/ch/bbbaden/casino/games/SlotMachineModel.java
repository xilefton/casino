package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;


public class SlotMachineModel extends Game {

    private NormalUser normalUser;
    private static  SlotMachineRow firstRow, secondRow, thirdRow;
    private static String urlOfFirstImage, urlOfSecondImage, urlOfThirdImage;
    private static Thread firstThread, secondThread, thirdThread;
    private static int winFactor = 0;
    private static boolean win = false;
    private static int betFactor = 1;
    private static int bet = 2;
    private static int betCoins;
    private static Image buttonImage;
    private static ArrayList<ThreeStarWin> threeStarWinArrayList  = new ArrayList<>();
    private static String urlOfThreeStarWinImage = "/images/supercherry/threestarwin/3-STAR-SELECTION.png";

    private static ArrayList<ThreeStarWin> addThreeStarWins() {
        threeStarWinArrayList.add(new ThreeStarWin("CHERRYCOLLECT", 5));
        threeStarWinArrayList.add(new ThreeStarWin("10X", 4));
        threeStarWinArrayList.add(new ThreeStarWin("4XSHUFFLE", 3));
        threeStarWinArrayList.add(new ThreeStarWin("2XSHUFFLE", 2));
        threeStarWinArrayList.add(new ThreeStarWin("FRUITSTOP", 1));
        return threeStarWinArrayList;
    }
    public static String getRandomThreeStarWin() {
        ArrayList<ThreeStarWin> threeStarWinArrayList;
        threeStarWinArrayList = addThreeStarWins();
        int randomNumber = (int) (Math.random() * 5);
        urlOfThreeStarWinImage = (threeStarWinArrayList.get(randomNumber).getImage());
        return urlOfThreeStarWinImage;
    }

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
    public static void spinFruits(ImageView first,ImageView second,ImageView third) {
        firstRow = new SlotMachineRow(first);
        firstThread = new Thread(firstRow);
        firstThread.start();
        secondRow = new SlotMachineRow(second);
        secondThread = new Thread(secondRow);
        secondThread.start();
        thirdRow = new SlotMachineRow(third);
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
        calculateWinFactor();
    }
    private static void calculateWinFactor() {
        if(urlOfFirstImage.equals(urlOfSecondImage) && urlOfFirstImage.equals((urlOfThirdImage))) {
            switch (urlOfFirstImage) {
                case "/src/main/resources/images/supercherry/fruits/STAR.png":
                    String selectWin = getRandomThreeStarWin();
                    switch (selectWin) {
                        case "/images/supercherry/fruits/FRUITSTOP.png":
                            winFactor = 100;
                            break;
                        case "/images/supercherry/fruits/2XSHUFFLE.png":
                            winFactor = 101;
                            break;
                        case "/images/supercherry/fruits/4XSHUFFLE.png":
                            winFactor = 102;
                            break;
                        case "/images/supercherry/fruits/10X.png":
                            winFactor = 103;
                            break;
                        case "/images/supercherry/fruits/CHERRYCOLLECT.png":
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
    public static void bet(Integer maxBet) {
        if(win) {
            betCoins++;
        } else {
            if(maxBet == 1) {
                betFactor = 1;
            }
            else if(maxBet <=4) {
                if(bet == 1) {
                    betFactor = 1;
                    bet++;
                } else {
                    betFactor = 2;
                    bet--;
                }
            }
            else if(maxBet <=9) {
                if(bet == 1) {
                    betFactor = 1;
                    bet++;
                } else if(bet == 2) {
                    betFactor = 2;
                    bet++;
                } else {
                    betFactor = 5;
                    bet = 1;
                }
            }
            else if(maxBet <=19) {
                if(bet == 1) {
                    betFactor = 1;
                    bet++;
                } else if(bet == 2) {
                    betFactor = 2;
                    bet++;
                } else if(bet == 3) {
                    betFactor = 5;
                    bet++;
                } else {
                    betFactor = 10;
                    bet = 1;
                }
            }
            else if(maxBet < 49) {
                if(bet == 1) {
                    betFactor = 1;
                    bet++;
                } else if(bet == 2) {
                    betFactor = 2;
                    bet++;
                } else if(bet == 3) {
                    betFactor = 5;
                    bet++;
                } else if(bet == 4){
                    betFactor = 10;
                    bet++;
                } else {
                    betFactor = 20;
                    bet = 1;
                }
            }
            else if(maxBet >= 50) {
                if(bet == 1) {
                    betFactor = 1;
                    bet++;
                } else if(bet == 2) {
                    betFactor = 2;
                    bet++;
                } else if(bet == 3) {
                    betFactor = 5;
                    bet++;
                } else if(bet == 4){
                    betFactor = 10;
                    bet++;
                } else if(bet == 5){
                    betFactor = 20;
                    bet++;
                } else {
                    betFactor = 50;
                    bet = 1;
                }
            }
        }
    }
    public static int getBetFactor() {return betFactor;}
    public static int getBetCoins() {return betCoins;}
    public static int getWinFactor() {return winFactor;}
    public static String getUrlOfThreeStarWinImage() {return urlOfThreeStarWinImage;}

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
