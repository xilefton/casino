package ch.bbbaden.casino.games.supercherry;

import ch.bbbaden.casino.CoinChangeReason;
import ch.bbbaden.casino.NormalUser;
import ch.bbbaden.casino.games.Game;
import ch.bbbaden.casino.scenes.ErrorType;

import java.sql.SQLException;
import java.util.Random;


public class SlotMachineModel extends Game {

    private SlotMachineRow row1 = new SlotMachineRow();
    private SlotMachineRow row2 = new SlotMachineRow();
    private SlotMachineRow row3 = new SlotMachineRow();
    private int row1Iterations;
    private int row2Iterations;
    private int row3Iterations;
    private int[] betFactor = new int[]{2, 5, 10, 20, 50};
    private int betFactorIndex = 0;
    private Fruit fruit1;
    private Fruit fruit2;
    private Fruit fruit3;
    private int winCoins = 0;
    private int winFactor = 0;
    private int usedGameCoins = 0;
    private boolean cherryCollect = false;


    public SlotMachineModel(NormalUser normalUser) {
        super("/fxml/SlotMachine.fxml", "Super Cherry", "/images/SuperCherry_Logo.png", normalUser, "SuperCherry");
    }

    public long getCoins() {
        try {
            return getNormalUser().getCoins();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void startGame() {
        Random random = new Random();
        int rN1 = (random.nextInt(25) + 25);
        int rN2 = (random.nextInt(10) + 40);
        int rN3 = (random.nextInt(30) + 20);
        row1.setIterations(rN1);
        row2.setIterations(rN2);
        row3.setIterations(rN3);
        notifyController();
    }

    public void startHoldGame(SlotMachineRow slotMachineRow) {
        Random random = new Random();
        int rN = (random.nextInt(25) + 10);
        slotMachineRow.setIterations(rN);
        notifyController();
    }

    private void startBonusGame() {
        Random random = new Random();
        int rN = random.nextInt(3);
        switch (rN) {
            case 1:
            case 2:
            case 4:
                winFactor = 0;
                break;
            case 3:
                switch (getBetFactor()) {
                    case 2:
                        winCoins = winCoins + 1;
                        break;
                    case 5:
                        winCoins = winCoins + 2;
                        break;
                    case 10:
                        winCoins = winCoins + 4;
                        break;
                    case 20:
                        winCoins = winCoins + 6;
                        break;
                    case 50:
                        winCoins = winCoins + 20;
                        break;
                }
        }
        notifyController();
    }

    private void startThreeStarWinGame() {
        Random random = new Random();
        int rN = random.nextInt(5);
        switch (rN) {
            //Fruit-Stop
            case 0:
                break;
            //2x Shuffle
            case 1:
                for (int i = 0; i < 2; i++) {
                    startGame();
                }
                break;
            //4x Shuffle
            case 2:
                for (int i = 0; i < 4; i++) {
                    startGame();
                }
                break;
            //10x
            case 3:
                winCoins = winCoins * getBetFactor() * 10;
                notifyController();
                break;
            //Cherry Collect
            case 4:
                startGame();
                cherryCollect = true;
                break;
        }
    }

    public boolean getCherryCollect() {
        return cherryCollect;
    }

    public int getBetFactor() {
        return betFactor[betFactorIndex];
    }

    public SlotMachineRow getRow1() {
        return row1;
    }

    public SlotMachineRow getRow2() {
        return row2;
    }

    public SlotMachineRow getRow3() {
        return row3;
    }

    public int getRow1Iterations() {
        int iterations = row1Iterations;
        row1Iterations = 0;
        return iterations;
    }

    public int getRow2Iterations() {
        int iterations = row2Iterations;
        row2Iterations = 0;
        return iterations;
    }

    public int getRow3Iterations() {
        int iterations = row3Iterations;
        row3Iterations = 0;
        return iterations;
    }

    public void mystery() {
        Random random = new Random();
        int rN1 = random.nextInt(2);
        int rN2 = random.nextInt(31);
        if (rN1 == 1) {
            if (rN2 <= 15) {
                winCoins = winCoins * 2;
            } else if (rN2 <= 25) {
                winCoins = winCoins * 3;
            } else if (rN2 <= 30) {
                winCoins = winCoins * 5;
            }
        } else {
            winCoins = 0;
            startBonusGame();
        }
        notifyController();
    }

    public void gamble() {
        Random random = new Random();
        int rN = random.nextInt(2);
        if (rN == 0) {
            winCoins = winCoins * 2;
        } else {
            winCoins = 0;
        }
        notifyController();
    }

    public void changeBetFactor() {
        if (betFactorIndex < 4) {
            betFactorIndex++;
        } else if (betFactorIndex == 4) {
            betFactorIndex = 0;
        }
        notifyController();
    }

    public void bet(long coins) {
        changeCoins(-coins, CoinChangeReason.PLAYER_BET);
    }

    public void changeCoins(long coins, CoinChangeReason reason) {
        try {
            getNormalUser().changeCoins(coins, reason);
        } catch (SQLException e) {
            showErrorMessage("", "", ErrorType.CONNECTION);
        }
    }

    public void countCherry(SlotMachineRow slotMachineRow, int rowNumber) {
        switch (rowNumber) {
            case 1:
                fruit1 = slotMachineRow.getFruit();
                break;
            case 2:
                fruit2 = slotMachineRow.getFruit();
                break;
            case 3:
                fruit3 = slotMachineRow.getFruit();
                break;
            default:
                break;
        }
        if (
                fruit1.getFruitType().equals(FruitType.CHERRY) ||
                        fruit2.getFruitType().equals(FruitType.CHERRY) ||
                        fruit3.getFruitType().equals(FruitType.CHERRY)) {
            winFactor = 2;
        } else {
            startBonusGame();
        }
    }

    public void handleSpinResults(SlotMachineRow slotMachineRow, int rowNumber) {
        switch (rowNumber) {
            case 1:
                fruit1 = slotMachineRow.getFruit();
                break;
            case 2:
                fruit2 = slotMachineRow.getFruit();
                break;
            case 3:
                fruit3 = slotMachineRow.getFruit();
                break;
            default:
                break;
        }
        if (fruit1 != null && fruit2 != null && fruit3 != null) {
            if (fruit1.equals(fruit2) && fruit1.equals(fruit3)) {
                switch (fruit1.getFruitType()) {
                    case BELL:
                        winFactor = 50;
                        break;
                    case CHERRY:
                        winFactor = 20;
                        break;
                    case GRAPES:
                    case POTATO:
                        winFactor = 2;
                        break;
                    case LEMON:
                    case STRAWBERRY:
                        winFactor = 5;
                        break;
                    case MELON:
                    case PEACH:
                        winFactor = 10;
                        break;
                    case STAR:
                        startThreeStarWinGame();
                        break;
                }
            } else if (fruit1.equals(fruit2)) {
                startHoldGame(row3);
            } else if (fruit1.equals(fruit3)) {
                startHoldGame(row2);
            } else if (fruit3.equals(fruit2)) {
                startHoldGame(row1);
            } else if (
                    fruit1.getFruitType().equals(FruitType.CHERRY) && fruit2.getFruitType().equals(FruitType.CHERRY) ||
                            fruit1.getFruitType().equals(FruitType.CHERRY) && fruit3.getFruitType().equals(FruitType.CHERRY) ||
                            fruit2.getFruitType().equals(FruitType.CHERRY) && fruit3.getFruitType().equals(FruitType.CHERRY)) {
                winFactor = 4;
            } else if (
                    fruit1.getFruitType().equals(FruitType.CHERRY) ||
                            fruit2.getFruitType().equals(FruitType.CHERRY) ||
                            fruit3.getFruitType().equals(FruitType.CHERRY)) {
                winFactor = 2;
            } else {
                startBonusGame();
            }
            setWin();
        }
    }

    private void setWin() {
        winCoins = usedGameCoins * winFactor;
        notifyController();
    }

    public int getWin() {
        return winCoins;
    }

    public void resetWin() {
        winCoins = 0;
        notifyController();
    }

    public void setUsedGameCoins(int usedGameCoins) {
        this.usedGameCoins = usedGameCoins;
    }

    public void playerWin(long gameCoins) {
        changeCoins(gameCoins, CoinChangeReason.PLAYER_WIN_OR_LOSS);
    }

    public void quit() {
        close();
    }
}
