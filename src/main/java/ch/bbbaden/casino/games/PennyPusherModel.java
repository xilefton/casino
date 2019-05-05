package ch.bbbaden.casino.games;

import ch.bbbaden.casino.NormalUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class PennyPusherModel extends Game {

    private int[][] field = new int[6][13];
    private boolean btn_push_disabled = true;
    private boolean btn_slot1_disabled = false;
    private boolean btn_slot2_disabled = false;
    private boolean btn_slot3_disabled = false;
    private Random rnd = new Random();
    private ArrayList<FieldChange> fieldChanges = new ArrayList<>();
    private int roundProfit;

    public PennyPusherModel(NormalUser normalUser) {
        super("/fxml/PennyPusher.fxml", "Penny Pusher", "/images/pennypusher_logo.png", normalUser);
        generateField();
    }

    private void generateField() {
        for (int i = 1; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = rnd.nextInt(6);
            }
        }
    }

    int getRoundProfit() {
        return roundProfit;
    }

    boolean isBtn_push_disabled() {
        return btn_push_disabled;
    }

    boolean isBtn_slot1_disabled() {
        return btn_slot1_disabled;
    }

    boolean isBtn_slot2_disabled() {
        return btn_slot2_disabled;
    }

    boolean isBtn_slot3_disabled() {
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
        try {
            getNormalUser().addCoins(-1, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        field[rnd.nextInt(1)][rnd.nextInt(4)]++;
        btn_push_disabled = false;
        btn_slot1_disabled = true;
        notifyController();
    }

    void slot2() {

        try {
            getNormalUser().addCoins(-1, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        field[rnd.nextInt(1)][4 + rnd.nextInt(4)]++;
        btn_push_disabled = false;
        btn_slot2_disabled = true;
        notifyController();
    }

    void slot3() {

        try {
            getNormalUser().addCoins(-1, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        int beforeRound = 0;
        try {
            beforeRound = getNormalUser().getCoins();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
                for (int k = 0; k < movingCoins; k++) {
                    FieldChange fieldChange = new FieldChange();
                    fieldChange.setStartX(j);
                    fieldChange.setStartY(i);
                    field[i][j]--;
                    int direction = rnd.nextInt(6);
                    if (direction == 0) {
                        fieldChange.setEndX(j);
                        fieldChange.setEndY(i - 1);
                        field[i - 1][j]++;
                    } else if (direction <= 2) {
                        if (rnd.nextBoolean()) {
                            if (j - 1 >= 0)
                                field[i][j - 1]++;
                            fieldChange.setEndX(j);
                            fieldChange.setEndY(i - 1);
                        } else {
                            if (j + 1 < 13)
                                field[i][j + 1]++;
                            fieldChange.setEndX(j + 1);
                            fieldChange.setEndY(i);
                        }
                    } else {
                        if (i + 1 >= 6) {
                            try {
                                getNormalUser().addCoins(1, false);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        } else
                            field[i + 1][j]++;
                        fieldChange.setEndX(j);
                        fieldChange.setEndY(i + 1);
                    }
                    fieldChanges.add(fieldChange);
                }
            }
        }

        try {
            cleanup();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            roundProfit = beforeRound - getNormalUser().getCoins();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btn_slot1_disabled = false;
        btn_slot2_disabled = false;
        btn_slot3_disabled = false;
        notifyController();
    }

    private void cleanup() throws SQLException {
        //in case there are too many coins on one field they get cleaned up
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] > 6) {
                    FieldChange fieldChange = new FieldChange();
                    field[i][j] = 6;
                    fieldChange.setStartX(j);
                    fieldChange.setStartY(i);
                    if (!(i + 1 >= 6)) {
                        field[i + 1][j] += field[i][j] - 6;
                        fieldChange.setEndX(j);
                        fieldChange.setEndY(i + 1);
                    } else getNormalUser().addCoins(1, false);
                    fieldChanges.add(fieldChange);
                }
            }
        }
    }

    ArrayList<FieldChange> getFieldChanges() {
        return fieldChanges;
    }

    int[][] getField() {
        return field;
    }
}
