package ch.bbbaden.casino.games.pennypusher;

import ch.bbbaden.casino.CoinChangeReason;
import ch.bbbaden.casino.NormalUser;
import ch.bbbaden.casino.games.Game;
import ch.bbbaden.casino.scenes.ErrorType;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;

public class PennyPusherModel extends Game {

    private int[][] field = new int[6][13];
    private boolean btn_push_disabled = true;
    private boolean btn_slot1_disabled = false;
    private boolean btn_slot2_disabled = false;
    private boolean btn_slot3_disabled = false;
    private Random rnd = new Random();
    private HashSet<FieldChange> fieldChanges = new HashSet<>(20);
    private long roundProfit;

    public PennyPusherModel(NormalUser normalUser) {
        super("/fxml/PennyPusher.fxml", "Penny Pusher", "/images/pennypusher_logo.png", normalUser, "pennypusher");
        generateField();
    }

    private void generateField() {
        for (int i = 1; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = rnd.nextInt(6);
            }
        }
    }

    long getRoundProfit() {
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
            return Long.toString(getNormalUser().getCoins());
        } catch (SQLException e) {
            showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
        }
        return null;
    }

    void slot1() {
        try {
            getNormalUser().changeCoins(-1, CoinChangeReason.PLAYER_BET);
        } catch (SQLException e) {
            showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
        }
        field[rnd.nextInt(1)][rnd.nextInt(4)]++;
        btn_push_disabled = false;
        btn_slot1_disabled = true;
        notifyController();
    }

    void slot2() {

        try {
            getNormalUser().changeCoins(-1, CoinChangeReason.PLAYER_BET);
        } catch (SQLException e) {
            showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
        }


        field[rnd.nextInt(1)][4 + rnd.nextInt(4)]++;
        btn_push_disabled = false;
        btn_slot2_disabled = true;
        notifyController();
    }

    void slot3() {

        try {
            System.out.println("-1");
            getNormalUser().changeCoins(-1, CoinChangeReason.PLAYER_BET);
        } catch (SQLException e) {
            showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
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

        fieldChanges.clear();
        long beforeRound = 0;

        try {
            beforeRound = getNormalUser().getCoins();
        } catch (SQLException e) {
            showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
        }

        for (int i = 0; i < field[0].length; i++) {
            if (field[0][i] > 0) {
                FieldChange fieldChange = new FieldChange();
                fieldChange.setStartX(i);
                fieldChange.setEndX(i);
                fieldChange.setStartY(0);
                fieldChange.setEndY(1);
                fieldChanges.add(fieldChange);
            }
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
                    if (field[i][j] > 0)
                        field[i][j]--;

                    int direction = rnd.nextInt(6);
                    if (direction == 0) {
                        fieldChange.setEndX(j);
                        fieldChange.setEndY(i - 1);
                        field[i - 1][j]++;
                        fieldChanges.add(fieldChange);
                    } else if (direction <= 2) {
                        if (rnd.nextBoolean()) {
                            if (j - 1 >= 0) {
                                field[i][j - 1]++;
                                fieldChange.setEndX(j - 1);
                                fieldChange.setEndY(i);
                                fieldChanges.add(fieldChange);
                            } else if (i == 6) {
                                try {
                                    getNormalUser().changeCoins(1, CoinChangeReason.PLAYER_WIN_OR_LOSS);
                                } catch (SQLException e) {
                                    showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
                                }
                            }
                        } else {
                            if (j + 1 < 13) {
                                field[i][j + 1]++;
                                fieldChange.setEndX(j + 1);
                                fieldChange.setEndY(i);
                                fieldChanges.add(fieldChange);
                            } else if (i == 6) {
                                try {
                                    getNormalUser().changeCoins(1, CoinChangeReason.PLAYER_WIN_OR_LOSS);
                                } catch (SQLException e) {
                                    showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
                                }
                            }
                        }
                    } else {
                        if (i + 1 >= 6) {
                            try {
                                getNormalUser().changeCoins(1, CoinChangeReason.PLAYER_WIN_OR_LOSS);
                            } catch (SQLException e) {
                                showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
                            }
                        } else {
                            field[i + 1][j]++;
                            fieldChange.setEndX(j);
                            fieldChange.setEndY(i + 1);
                            fieldChanges.add(fieldChange);
                        }
                    }
                }
            }
        }

        notifyController();
        cleanup();

        try {
            roundProfit = getNormalUser().getCoins() - beforeRound;
        } catch (SQLException e) {
            showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
        }

        if (firstRowPopulated()) {
            btn_push_disabled = false;
        }

        btn_slot1_disabled = false;
        btn_slot2_disabled = false;
        btn_slot3_disabled = false;
        notifyController();
        fieldChanges.clear();
    }

    private boolean firstRowPopulated() {
        for (int i = 0; i < field[0].length; i++) {
            if (field[0][i] != 0)
                return true;
        }
        return false;
    }

    private void cleanup() {
        //in case there are too many coins on one field they get cleaned up
        for (int i = 0; i < field.length; i++) {
            fieldChanges.clear();
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
                        fieldChanges.add(fieldChange);
                    } else {
                        try {
                            getNormalUser().changeCoins(1, CoinChangeReason.PLAYER_WIN_OR_LOSS);
                        } catch (SQLException e) {
                            showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
                        }
                    }
                }
            }
        }
    }

    int[][] getField() {
        return field;
    }

    HashSet<FieldChange> getFieldChanges() {
        return fieldChanges;
    }
}
