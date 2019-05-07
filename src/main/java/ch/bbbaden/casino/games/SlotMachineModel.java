package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;


public class SlotMachineModel extends Game {

    private NormalUser normalUser;
    private SlotMachineRow firstRow, secondRow, thirdRow, holdRow;
    private String urlOfFirstImage, urlOfSecondImage, urlOfThirdImage;
    private Thread firstThread, secondThread, thirdThread, holdThread;
    private int winFactor = 0;
    private boolean win = false;
    private int betFactor = 1;
    private int bet = 2;
    private int betCoins;
    private Image buttonImage;
    private ArrayList<ThreeStarWin> threeStarWinArrayList  = new ArrayList<>();
    private String urlOfThreeStarWinImage = "src/main/resources/images/supercherry/threestarwin/3-STAR-SELECTION.png";

    private ArrayList<ThreeStarWin> addThreeStarWins() {
        threeStarWinArrayList.add(new ThreeStarWin("CHERRYCOLLECT", 5));
        threeStarWinArrayList.add(new ThreeStarWin("10X", 4));
        threeStarWinArrayList.add(new ThreeStarWin("4XSHUFFLE", 3));
        threeStarWinArrayList.add(new ThreeStarWin("2XSHUFFLE", 2));
        threeStarWinArrayList.add(new ThreeStarWin("FRUITSTOP", 1));
        return threeStarWinArrayList;
    }
    public String getRandomThreeStarWin() {
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
    public void spinFruits(ImageView first,ImageView second,ImageView third) {
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
    public void stopSpinning() {
        firstThread.stop();
        secondThread.stop();
        thirdThread.stop();
        urlOfFirstImage=firstRow.getUrlOfImage();
        urlOfSecondImage=secondRow.getUrlOfImage();
        urlOfThirdImage=thirdRow.getUrlOfImage();
        calculateWinFactor();
    }
    public void stopHold(SlotMachineRow slotMachineRow, int i) {
        holdThread.stop();
        switch (i) {
            case 1:
                urlOfFirstImage  = slotMachineRow.getUrlOfImage();
                break;
            case 2:
                urlOfSecondImage = slotMachineRow.getUrlOfImage();
                break;
            case 3:
                urlOfThirdImage = slotMachineRow.getUrlOfImage();
        }
        calculateWinFactor();
    }
    public void stopStep(SlotMachineRow slotMachineRow, int i) {
        holdThread.stop();
        switch (i) {
            case 1:
                urlOfFirstImage  = slotMachineRow.getUrlOfImage();
                break;
            case 2:
                urlOfSecondImage = slotMachineRow.getUrlOfImage();
                break;
            case 3:
                urlOfThirdImage = slotMachineRow.getUrlOfImage();
        }
        winFactor = 20;
    }
    private void calculateWinFactor() {
        if(urlOfFirstImage.equals(urlOfSecondImage) && urlOfFirstImage.equals((urlOfThirdImage))) {
            switch (urlOfFirstImage) {
                case "/images/supercherry/fruits/STAR.png":
                    String selectWin = getRandomThreeStarWin();
                    switch (selectWin) {
                        case "/images/supercherry/fruits/FRUITSTOP.png":
                            winFactor = 100;
                            win = true;
                            break;
                        case "/images/supercherry/fruits/2XSHUFFLE.png":
                            winFactor = 101;
                            win = true;
                            break;
                        case "/images/supercherry/fruits/4XSHUFFLE.png":
                            winFactor = 102;
                            win = true;
                            break;
                        case "/images/supercherry/fruits/10X.png":
                            winFactor = 103;
                            win = true;
                            break;
                        case "/images/supercherry/fruits/CHERRYCOLLECT.png":
                            winFactor = 104;
                            win = true;
                            break;
                    }
                    break;
                case "/images/supercherry/fruits/BELL.png":
                    winFactor = 50;
                    win = true;
                    break;
                case "/images/supercherry/fruits/CHERRY.png":
                    winFactor = 20;
                    win = true;
                    break;
                case "/images/supercherry/fruits/PEACH.png":
                case "/images/supercherry/fruits/MELON.png":
                    winFactor = 10;
                    win = true;
                    break;
                case "/images/supercherry/fruits/STRAWBERRY.png":
                case "/images/supercherry/fruits/LEMON.png":
                    winFactor = 5;
                    win = true;
                    break;
                case "/images/supercherry/fruits/POTATO.png":
                case "/images/supercherry/fruits/GRAPES.png":
                    winFactor = 2;
                    win = true;
                    break;
            }
        }
        else if (urlOfFirstImage.equals(urlOfSecondImage) || urlOfSecondImage.equals(urlOfThirdImage) || urlOfFirstImage.equals(urlOfThirdImage)) {
            if (urlOfFirstImage.equals("/images/supercherry/fruits/CHERRY.png") &&
                    urlOfSecondImage.equals("/images/supercherry/fruits/CHERRY.png")) {
                winFactor = 6;
            } else if (urlOfSecondImage.equals("/images/supercherry/fruits/CHERRY.png") &&
                    urlOfThirdImage.equals("/images/supercherry/fruits/CHERRY.png")) {
                winFactor = 7;
            } else if (urlOfFirstImage.equals("/images/supercherry/fruits/CHERRY.png") &&
                    urlOfThirdImage.equals("/images/supercherry/fruits/CHERRY.png")) {
                winFactor = 8;
            } else if (urlOfFirstImage.equals(urlOfSecondImage)) {
                winFactor = 1;
            } else if(urlOfSecondImage.equals(urlOfThirdImage)) {
                winFactor = 9;
            } else if(urlOfFirstImage.equals(urlOfThirdImage)) {
                winFactor = 11;
            } else if(urlOfFirstImage.equals("/images/supercherry/fruits/CHERRY.png/") || urlOfSecondImage.equals("/images/supercherry/fruits/CHERRY.png/") || urlOfThirdImage.equals("/images/supercherry/fruits/CHERRY.png/")) {
                winFactor = 12;
            }
        } else winFactor = 0;
        System.out.println(winFactor);
    }
    public void step(ImageView imageView, int i) {
        holdRow = new SlotMachineRow(imageView);
        holdThread = new Thread(holdRow);
        holdThread.start();
        PauseTransition transition = new PauseTransition(Duration.seconds(5));
        transition.setOnFinished(event -> {
            switch (i) {
                case 1:
                    stopStep(firstRow, 1);
                    urlOfFirstImage = "/images/supercherry/fruits/CHERRY.png";
                    win = true;
                    break;
                case 2:
                    stopStep(secondRow, 2);
                    urlOfSecondImage = "/images/supercherry/fruits/CHERRY.png";
                    win = true;
                    break;
                case 3:
                    stopStep(thirdRow, 3);
                    urlOfThirdImage = "/images/supercherry/fruits/CHERRY.png";
                    win = true;
                    break;
            }
            imageView.setImage(setButtonImage("/src/main/resources/images/supercherry/fruits/CHERRY.png"));
        });
        transition.play();
    }
    public void hold(ImageView imageView, int i) {
        holdRow = new SlotMachineRow(imageView);
        holdThread = new Thread(holdRow);
        holdThread.start();
        PauseTransition transition = new PauseTransition(Duration.seconds(5));
        transition.setOnFinished(event -> {
            switch (i) {
                case 1:
                    stopHold(firstRow, 1);
                    break;
                case 2:
                    stopHold(secondRow, 2);
                    break;
                case 3:
                    stopHold(thirdRow, 3);
            }

        });
        transition.play();
    }
    public void gamble() {
        int randomNumber = (int) (Math.random() * 2);
        if(randomNumber == 1) {
            winFactor = 4;
        } else {
            winFactor = 0;
        }
    }
    public void bet(Integer maxBet) {
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
    public int getBetFactor() {return betFactor;}
    public int getBetCoins() {return betCoins;}
    public int getWinFactor() {return winFactor;}
    public String getUrlOfThreeStarWinImage() {return urlOfThreeStarWinImage;}
    public boolean getWin() {return win;}

    public void mystery() {
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
    public Image setButtonImage(String path) {
        File file = new File(path);
        try {
            buttonImage = new Image(file.toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return buttonImage;
    }
}
