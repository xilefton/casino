package ch.bbbaden.casino.games;

import ch.bbbaden.casino.NormalUser;

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
    private int betCoins = 0;
    private int winCoins = 0;
    private int winFactor = 0;

    public SlotMachineModel(NormalUser normalUser) {
        super("/fxml/SlotMachine.fxml", "Super Cherry", "/images/SuperCherry_Logo.png", normalUser);
    }

    public int getCoins() {
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
    }
    private void startBonusGame() {
    }

    private void startThreeStarWinGame() {
    }
    public void gamble() {
    }
    public int getBetFactor() {
        return betFactor[betFactorIndex];
    }

    /*public int getBetCoins() {
        return betCoins;
    }*/

    public int getWinFactor() {
        return winFactor;
    }
    /*public boolean getWin() {
        return win;
    }*/

    /*public boolean getBonus() {
        return bonus;
    }*/

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
        int rN = (int) (Math.random() * 2);
        int randomNumber = (int) (Math.random() * 30);
        if (rN == 1) {
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

    public void changeBetFactor() {
        if (betFactorIndex < betFactor.length) {
            betFactorIndex++;
        } else {
            betFactorIndex = 0;
        }
        notifyController();
    }

    public void bet(int coins) {
        betCoins = coins;
        changeCoins(-coins);
    }

    public void changeCoins(int coins) {
        try {
            getNormalUser().addCoins(coins, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleSpinResults(SlotMachineRow slotMachineRow, int rowNumber) {
        System.out.println("handleSpinResults");
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
                }
                else if(fruit1.equals(fruit2)) {
                    startHoldGame(row3); }
                else if(fruit1.equals(fruit3)) {
                    startHoldGame(row2); }
                else if(fruit3.equals(fruit2)) {
                    startHoldGame(row1); }
                else if (
                    fruit1.getFruitType().equals(FruitType.CHERRY) && fruit2.getFruitType().equals(FruitType.CHERRY) ||
                    fruit1.getFruitType().equals(FruitType.CHERRY) && fruit3.getFruitType().equals(FruitType.CHERRY) ||
                    fruit2.getFruitType().equals(FruitType.CHERRY) && fruit3.getFruitType().equals(FruitType.CHERRY)) {
                winFactor = 4;
                }
                  else if(
                    fruit1.getFruitType().equals(FruitType.CHERRY) ||
                    fruit2.getFruitType().equals(FruitType.CHERRY) ||
                    fruit3.getFruitType().equals(FruitType.CHERRY)) {
                winFactor = 2;
            }
            else {
                startBonusGame();
            }
        }
    }
}
