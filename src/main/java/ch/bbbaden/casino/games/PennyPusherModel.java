package ch.bbbaden.casino.games;

import ch.bbbaden.casino.NormalUser;

import java.sql.SQLException;
import java.util.Random;

public class PennyPusherModel extends Game {

    private int[][] field = new int[6][13];
    private boolean btn_push_disabled = true;
    private boolean btn_slot1_disabled = false;
    private boolean btn_slot2_disabled = false;
    private boolean btn_slot3_disabled = false;
    private Random rnd = new Random();

    public PennyPusherModel(NormalUser normalUser) {
        super("/fxml/PennyPusher.fxml", "Penny Pusher", "/images/PennyPusher_Logo.png", normalUser);
    }

    public boolean isBtn_push_disabled() {
        return btn_push_disabled;
    }

    public boolean isBtn_slot1_disabled() {
        return btn_slot1_disabled;
    }

    public boolean isBtn_slot2_disabled() {
        return btn_slot2_disabled;
    }

    public boolean isBtn_slot3_disabled() {
        return btn_slot3_disabled;
    }

    public String getCoins() {
        try {
            return Integer.toString(getNormalUser().getCoins());
        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    void slot1() {
        getNormalUser().addCoins(-1);
        field[rnd.nextInt(1)][rnd.nextInt(4)]++;
        btn_push_disabled = false;
        btn_slot1_disabled = true;
        notifyController();
    }

    void slot2() {
        getNormalUser().addCoins(-1);
        field[rnd.nextInt(1)][4 + rnd.nextInt(4)]++;
        btn_push_disabled = false;
        btn_slot2_disabled = true;
        notifyController();
    }

    void slot3() {
        getNormalUser().addCoins(-1);
        field[0][8 + rnd.nextInt(4)]++;
        btn_push_disabled = false;
        btn_slot3_disabled = true;
        notifyController();
    }

    void push() {
        btn_push_disabled = true;
        btn_slot1_disabled = true;
        btn_slot2_disabled = true;
        btn_slot3_disabled = true;
        notifyController();

        for (int i = 0; i < field[0].length; i++) {
            field[1][i] += field[0][i];
            field[0][i] = 0;
        }

        for (int i = 1; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                int movingCoins;
                if (i == 6 || j == 0 || j == 13) {
                    movingCoins = (rnd.nextInt(6) + field[i][j]) - 6;
                } else {
                    movingCoins = (rnd.nextInt(5) + field[i][j]) - 6;
                }
                if (movingCoins < 0) {
                    movingCoins = 0;
                }
                field[i][j] -= movingCoins;
                for (int k = 0; k < movingCoins; k++) {
                    int direction = rnd.nextInt(6);
                    System.out.println(direction);
                    if (direction == 0) {
                        field[i - 1][j]++;
                    } else if (direction <= 2) {
                        if (rnd.nextBoolean()) {
                            if (j - 1 >= 0)
                                field[i][j - 1]++;
                        } else {
                            if (j + 1 <= 13)
                                field[i][j + 1]++;
                        }
                    } else {
                        if (i + 1 >= 6)
                            getNormalUser().addCoins(1);
                        else
                            field[i + 1][j]++;
                    }
                }
            }
        }

        btn_slot1_disabled = false;
        btn_slot2_disabled = false;
        btn_slot3_disabled = false;
        notifyController();
    }

    public int[][] getField() {
        return field;
    }
}
