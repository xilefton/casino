package ch.bbbaden.casino.games.roulette;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RouletteController implements Controller {
    public Button btn_block;
    public Label label_coins;
    public ImageView imgV_quit;
    public ImageView imgV_spin;
    public ImageView imgV_spinWheel;
    public Label label_rndNumber;
    public Label label_win;
    public Button btn_zero;
    public Button btn_doubleZero;
    public AnchorPane anchorPane;
    public ImageView imgV_jeton5;
    public ImageView imgV_jeton10;
    public ImageView imgV_jeton25;
    public ImageView imgV_jeton50;
    public ImageView imgV_jeton100;
    public Button btn_first;
    public Button btn_second;
    public Button btn_third;
    public Button btn_black;
    public Button btn_1to18;
    public Button btn_even;
    public Button btn_red;
    public Button btn_odd;
    public Button btn_19to36;
    public Button btn_up;
    public Button btn_mid;
    public Button btn_down;
    public Button btn_five;
    public ImageView imgV_ballRotate;
    String[] numbers = new String[38];
    ArrayList<Integer> buttonwert = new ArrayList<>();
    ArrayList<ImageView> jetonentf = new ArrayList<>();
    HashMap<Integer, Integer> betrag = new HashMap();
    ArrayList<Integer> chips = new ArrayList<>();
    @FXML
    private GridPane gridPane;
    private RouletteModel rouletteModel;
    private boolean jetonausg = false;
    private boolean spinning = false;
    private int jeton = 0;
    private String image = "";
    private int selectedValue;

    // Jeton Bilder auf dem Feld per Klick
    private void jetonMaker(Button button, int offset) {
        if (jetonausg) {
            ImageView iv = new ImageView(image);
            anchorPane.getChildren().add(iv);
            jetonentf.add(iv);
            double nodeMinX = button.getLayoutBounds().getMinX();
            double nodeMinY = button.getLayoutBounds().getMinY();
            Point2D nodeInScene = button.localToScene(nodeMinX, nodeMinY);

            iv.relocate(nodeInScene.getX() - 10
                    + iv.getLayoutBounds().getMinX() - 49, nodeInScene.getY() - offset
                    + iv.getLayoutBounds().getMinY() - offset);
            iv.setScaleX(0.4);
            iv.setScaleY(0.4);
            rouletteModel.changeCoinsBet(selectedValue);
            update();
        }
    }

    // Jeton Bilder auf Gridpane setzen
    private void handleButtonAction(Button button, ActionEvent actionEvent) {
        jetonMaker(button, 35);
        setWert(button);
    }

    // Jeton Bild auf 0
    public void btn_zero_onAction(ActionEvent actionEvent) {
        jetonMaker(btn_zero, 30);
        buttonwert.add(0);
    }

    // Jeton Bild auf 00
    public void btn_doubleZero_onAction(ActionEvent actionEvent) {
        jetonMaker(btn_doubleZero, 30);
        buttonwert.add(100);
    }

    // Geldbetrag anzeigen
    public void update() {
        label_coins.setText("Ihr Geldbetrag: " + rouletteModel.getCoins());
    }

    public void initialize(Model model) {
        // Array für die Roulette Rad Zahlen
        numbers[0] = "00";
        numbers[1] = "0";
        for (int i = 2; i < 38; i++) {
            int c = i - 1;
            numbers[i] = "" + c;
        }

        // Unsichtbarer Button für das "Abbrechen" Bild
        btn_block.setShape(new Circle(10));
        rouletteModel = (RouletteModel) model;

        // Buttons im GridPane generieren
        int rows = gridPane.getRowConstraints().size();
        int columns = gridPane.getColumnConstraints().size();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Button button = new Button("Text");
                gridPane.add(button, column, row);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        handleButtonAction((Button) e.getSource(), e);
                    }
                });
                button.setOpacity(0);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            }
        }
        update();
    }

    // Jeton Bilder und Werte setzen
    public void handleJeton5(MouseEvent mouseEvent) {
        imgV_jeton5.setImage(new Image("/images/roulette/Jetons-5-Ausgewählt.png"));
        image = "/images/roulette/Jetons-5.png";
        imgV_jeton10.setImage(new Image("/images/roulette/jetons-10.png"));
        imgV_jeton25.setImage(new Image("/images/roulette/jetons-25.png"));
        imgV_jeton50.setImage(new Image("/images/roulette/jetons-50.png"));
        imgV_jeton100.setImage(new Image("/images/roulette/jetons-100.png"));
        jetonausg = true;
        selectedValue = 5;
        jeton = 5;
    }

    public void handleJeton10(MouseEvent mouseEvent) {
        imgV_jeton10.setImage(new Image("/images/roulette/Jetons-10-Ausgewählt.png"));
        image = "/images/roulette/Jetons-10.png";
        imgV_jeton5.setImage(new Image("/images/roulette/Jetons-5.png"));
        imgV_jeton25.setImage(new Image("/images/roulette/jetons-25.png"));
        imgV_jeton50.setImage(new Image("/images/roulette/jetons-50.png"));
        imgV_jeton100.setImage(new Image("/images/roulette/jetons-100.png"));
        jetonausg = true;
        selectedValue = 10;
        jeton = 10;
    }

    public void handleJeton25(MouseEvent mouseEvent) {
        imgV_jeton25.setImage(new Image("/images/roulette/Jetons-25-Ausgewählt.png"));
        image = "/images/roulette/Jetons-25.png";
        imgV_jeton5.setImage(new Image("/images/roulette/Jetons-5.png"));
        imgV_jeton10.setImage(new Image("/images/roulette/jetons-10.png"));
        imgV_jeton50.setImage(new Image("/images/roulette/jetons-50.png"));
        imgV_jeton100.setImage(new Image("/images/roulette/jetons-100.png"));
        jetonausg = true;
        selectedValue = 25;
        jeton = 25;
    }

    public void handleJeton50(MouseEvent mouseEvent) {
        imgV_jeton50.setImage(new Image("/images/roulette/Jetons-50-Ausgewählt.png"));
        image = "/images/roulette/Jetons-50.png";
        imgV_jeton5.setImage(new Image("/images/roulette/Jetons-5.png"));
        imgV_jeton10.setImage(new Image("/images/roulette/jetons-10.png"));
        imgV_jeton25.setImage(new Image("/images/roulette/jetons-25.png"));
        imgV_jeton100.setImage(new Image("/images/roulette/jetons-100.png"));
        jetonausg = true;
        selectedValue = 50;
        jeton = 50;
    }

    public void handleJeton100(MouseEvent mouseEvent) {
        imgV_jeton100.setImage(new Image("/images/roulette/Jetons-100-Ausgewählt.png"));
        image = "/images/roulette/Jetons-100.png";
        imgV_jeton5.setImage(new Image("/images/roulette/Jetons-5.png"));
        imgV_jeton10.setImage(new Image("/images/roulette/jetons-10.png"));
        imgV_jeton25.setImage(new Image("/images/roulette/jetons-25.png"));
        imgV_jeton50.setImage(new Image("/images/roulette/jetons-50.png"));
        jetonausg = true;
        selectedValue = 100;
        jeton = 100;
    }

    // Rotation des Roulette Rads
    public void handleDrehen(MouseEvent mouseEvent) {
        if (spinning) {

        } else {
            label_win.setText("");
            imgV_spin.setImage(new Image("/images/roulette/drehen_transp.png"));
            imgV_ballRotate.setImage(new Image("/images/roulette/rouletterad-ball.png"));
            RotateTransition rt = new RotateTransition(Duration.millis(3000), imgV_spinWheel);
            RotateTransition rtball = new RotateTransition(Duration.millis(3000), imgV_ballRotate);
            rt.setByAngle(750);
            rtball.setByAngle(-750);
            rt.setInterpolator(Interpolator.LINEAR);
            rtball.setInterpolator(Interpolator.LINEAR);
            rt.play();
            rtball.play();
            jetonausg = false;
            spinning = true;

            rt.setOnFinished(event -> {
                test();

                imgV_ballRotate.setImage(null);

                spinning = false;
                jetonausg = true;


                for (int i = 0; i < jetonentf.size(); i++) {
                    anchorPane.getChildren().remove(jetonentf.get(i));
                }

            });

        }
    }

    // Bilder der Buttons anpassen
    public void handledrepressing(MouseEvent mouseEvent) {
        if (spinning) {

        } else {
            imgV_spin.setImage(new Image("/images/roulette/drehen_ausg.png"));
        }
    }

    public void handledrerelease(MouseEvent mouseEvent) {
        if (spinning) {

        } else {
            imgV_spin.setImage(new Image("/images/roulette/drehen_button.png"));
        }
    }

    public void handlepressing(MouseEvent mouseEvent) {
        imgV_quit.setImage(new Image("/images/roulette/abbrechen_ausg.png"));
    }

    // Nummer aus dem Rad generieren
    private void test() {
        Random rnd = new Random();
        int d = rnd.nextInt(38);
        String rndnumber = numbers[d];
        label_rndNumber.setText("Gedrehte Nummer: " + rndnumber);
        if (rndnumber == "00") {
            rndnumber = "100";
        }

        imgV_spin.setImage(new Image("/images/roulette/drehen_button.png"));
        if (betrag.containsKey(Integer.parseInt(rndnumber))) {
            gewinn(Integer.parseInt(rndnumber));
        }
        betrag.clear();
    }

    // Gewinnanzeige
    private void gewinn(int d) {
        label_win.setText("Du hast " + betrag.get(d) + " gewonnen!");
        rouletteModel.changeCoinsWinOrLoss(betrag.get(d));
        update();
    }

    // Werte den Buttons zuordnen
    private void setWert(Button button) {
        //Obere Seite
        int column = GridPane.getColumnIndex(button);
        //Linke Seite
        int row = GridPane.getRowIndex(button);
        buttonwert.clear();

        chips.add(jeton);

        switch (row) {
            case 0:
                scColumn(column, 0, 1);
                break;
            case 1:
                scColumn(column, 0, 2);
                scColumn(column, 1, 2);
                break;
            case 2:
                scColumn(column, 1, 1);
                break;
            case 3:
                scColumn(column, 1, 2);
                scColumn(column, 2, 2);
                break;
            case 4:
                scColumn(column, 2, 1);
                break;
            case 5:
                scColumn(column, 0, 3);
                scColumn(column, 1, 3);
                scColumn(column, 2, 3);
                break;
        }
    }

    // Betrag wird ausgerechnet, durch das gestzte und die gedrehte Zahl
    private void addAmount(int i, int multiplicator) {
        int d = 0;
        if (betrag.containsKey(i)) {
            d = betrag.get(i);
            betrag.remove(i);
        }
        int mult = chips.get(chips.size() - 1) * multiplicator;
        d += mult;

        betrag.put(i, d);
    }

    private void scColumn(int column, int row, int divide) {
        int multiplicatorr = 36;
        if (column % 2 == 1) {
            multiplicatorr = 18;
        }

        multiplicatorr /= divide;

        switch (column) {
            case 0:
                buttonwert.add(3 - row);
                addAmount(3 - row, multiplicatorr);
                break;
            case 2:
                buttonwert.add(6 - row);
                addAmount(6 - row, multiplicatorr);
                break;
            case 4:
                buttonwert.add(9 - row);
                addAmount(9 - row, multiplicatorr);
                break;
            case 6:
                buttonwert.add(12 - row);
                addAmount(12 - row, multiplicatorr);
                break;
            case 8:
                buttonwert.add(15 - row);
                addAmount(15 - row, multiplicatorr);
                break;
            case 10:
                buttonwert.add(18 - row);
                addAmount(18 - row, multiplicatorr);
                break;
            case 12:
                buttonwert.add(21 - row);
                addAmount(21 - row, multiplicatorr);
                break;
            case 14:
                buttonwert.add(24 - row);
                addAmount(24 - row, multiplicatorr);
                break;
            case 16:
                buttonwert.add(27 - row);
                addAmount(27 - row, multiplicatorr);
                break;
            case 18:
                buttonwert.add(30 - row);
                addAmount(30 - row, multiplicatorr);
                break;
            case 20:
                buttonwert.add(33 - row);
                addAmount(33 - row, multiplicatorr);
                break;
            case 22:
                buttonwert.add(36 - row);
                addAmount(36 - row, multiplicatorr);
                break;
            case 1:
                buttonwert.add(3 - row);
                addAmount(3 - row, multiplicatorr);
                buttonwert.add(6 - row);
                addAmount(6 - row, multiplicatorr);
                break;
            case 3:
                buttonwert.add(6 - row);
                addAmount(6 - row, multiplicatorr);
                buttonwert.add(9 - row);
                addAmount(9 - row, multiplicatorr);
                break;
            case 5:
                buttonwert.add(9 - row);
                addAmount(9 - row, multiplicatorr);
                buttonwert.add(12 - row);
                addAmount(12 - row, multiplicatorr);
                break;
            case 7:
                buttonwert.add(12 - row);
                addAmount(12 - row, multiplicatorr);
                buttonwert.add(15 - row);
                addAmount(15 - row, multiplicatorr);
                break;
            case 9:
                buttonwert.add(15 - row);
                addAmount(15 - row, multiplicatorr);
                buttonwert.add(18 - row);
                addAmount(18 - row, multiplicatorr);
                break;
            case 11:
                buttonwert.add(18 - row);
                addAmount(18 - row, multiplicatorr);
                buttonwert.add(21 - row);
                addAmount(21 - row, multiplicatorr);
                break;
            case 13:
                buttonwert.add(21 - row);
                addAmount(21 - row, multiplicatorr);
                buttonwert.add(24 - row);
                addAmount(24 - row, multiplicatorr);
                break;
            case 15:
                buttonwert.add(24 - row);
                addAmount(24 - row, multiplicatorr);
                buttonwert.add(27 - row);
                addAmount(27 - row, multiplicatorr);
                break;
            case 17:
                buttonwert.add(27 - row);
                addAmount(27 - row, multiplicatorr);
                buttonwert.add(30 - row);
                addAmount(30 - row, multiplicatorr);
                break;
            case 19:
                buttonwert.add(30 - row);
                addAmount(30 - row, multiplicatorr);
                buttonwert.add(33 - row);
                addAmount(33 - row, multiplicatorr);
                break;
            case 21:
                buttonwert.add(33 - row);
                addAmount(33 - row, multiplicatorr);
                buttonwert.add(36 - row);
                addAmount(36 - row, multiplicatorr);
                break;
        }
    }

    // Verschieden Bilder für die Buttons
    public void drehenter(MouseEvent mouseEvent) {
        if (spinning) {

        } else {
            imgV_spin.setImage(new Image("/images/roulette/drehen_ausg.png"));
        }
    }

    public void drehexit(MouseEvent mouseEvent) {
        if (spinning) {

        } else {
            imgV_spin.setImage(new Image("/images/roulette/drehen_button.png"));
        }
    }

    public void abbenter(MouseEvent mouseEvent) {
        imgV_quit.setImage(new Image("/images/roulette/abbrechen_ausg.png"));
    }


    public void abbexit(MouseEvent mouseEvent) {
        imgV_quit.setImage(new Image("/images/roulette/abbrechen_button.png"));
    }

    // Spiel schliesst sich. Zurück auf Startseite/Menü.
    public void imgV_quit_onMouseClick(MouseEvent mouseEvent) {
        rouletteModel.close();
    }

    // Die anderen Buttons auf dem Feld
    public void btn_first_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            buttonwert.clear();
            jetonMaker(btn_first, 40);
            chips.add(jeton);
            numbersUpperLeft(3);
            numbersMiddleLeft(3);
            numbersBottomLeft(3);
        }
    }

    public void btn_second_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            buttonwert.clear();
            jetonMaker(btn_second, 40);
            chips.add(jeton);
            numbersUpperMid(3);
            numbersMiddleMid(3);
            numbersBottomID(3);
        }
    }

    public void btn_third_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            buttonwert.clear();
            jetonMaker(btn_third, 40);
            chips.add(jeton);
            numbersUpperRight(3);
            numbersMiddleRight(3);
            numbersBottomRight(3);
        }
    }

    public void btn_black_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            buttonwert.clear();
            jetonMaker(btn_black, 40);
            chips.add(jeton);
            addAmount(2, 2);
            addAmount(4, 2);
            addAmount(6, 2);
            addAmount(8, 2);
            addAmount(10, 2);
            addAmount(11, 2);
            addAmount(13, 2);
            addAmount(15, 2);
            addAmount(17, 2);
            addAmount(20, 2);
            addAmount(22, 2);
            addAmount(24, 2);
            addAmount(26, 2);
            addAmount(28, 2);
            addAmount(29, 2);
            addAmount(31, 2);
            addAmount(33, 2);
            addAmount(35, 2);
        }
    }

    public void btn_1to18_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            buttonwert.clear();
            jetonMaker(btn_1to18, 40);
            chips.add(jeton);
            numbersUpperLeft(2);
            numbersMiddleLeft(2);
            numbersBottomLeft(2);
            addAmount(13, 2);
            addAmount(14, 2);
            addAmount(15, 2);
            addAmount(16, 2);
            addAmount(17, 2);
            addAmount(18, 2);
        }
    }

    public void btn_even_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(btn_even, 40);

            for (int i = 2; i <= 36; i += 2) {
                addAmount(i, 2);
            }
        }
    }

    public void btn_red_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(btn_red, 40);
            addAmount(1, 2);
            addAmount(3, 2);
            addAmount(5, 2);
            addAmount(7, 2);
            addAmount(9, 2);
            addAmount(12, 2);
            addAmount(14, 2);
            addAmount(16, 2);
            addAmount(18, 2);
            addAmount(19, 2);
            addAmount(21, 2);
            addAmount(23, 2);
            addAmount(25, 2);
            addAmount(27, 2);
            addAmount(30, 2);
            addAmount(32, 2);
            addAmount(34, 2);
            addAmount(36, 2);
        }
    }

    public void btn_odd_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(btn_odd, 40);

            for (int i = 1; i <= 35; i += 2) {
                addAmount(i, 2);
            }
        }
    }

    public void btn_19to36_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(btn_19to36, 40);
            numbersUpperRight(2);
            numbersMiddleRight(2);
            numbersBottomRight(2);
            addAmount(19, 2);
            addAmount(20, 2);
            addAmount(21, 2);
            addAmount(22, 2);
            addAmount(23, 2);
            addAmount(24, 2);
        }
    }

    public void btn_up_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(btn_up, 40);
            numbersUpperLeft(3);
            numbersUpperMid(3);
            numbersUpperRight(3);
        }
    }

    public void btn_mid_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(btn_mid, 40);
            numbersMiddleLeft(3);
            numbersMiddleMid(3);
            numbersMiddleRight(3);
        }
    }

    public void btn_down_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(btn_down, 40);
            numbersBottomLeft(3);
            numbersBottomID(3);
            numbersBottomRight(3);
        }
    }

    public void btn_five_onAction(ActionEvent actionEvent) {
        if (jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(btn_five, 40);
            addAmount(0, 7);
            addAmount(100, 7);
            addAmount(1, 7);
            addAmount(2, 7);
            addAmount(3, 7);
        }
    }

    //Funktionen für Zahlfelder
    private void numbersUpperLeft(int multi) {
        addAmount(3, multi);
        addAmount(6, multi);
        addAmount(9, multi);
        addAmount(12, multi);
    }

    private void numbersUpperMid(int multi) {
        addAmount(15, multi);
        addAmount(18, multi);
        addAmount(21, multi);
        addAmount(24, multi);
    }

    private void numbersUpperRight(int multi) {
        addAmount(27, multi);
        addAmount(30, multi);
        addAmount(33, multi);
        addAmount(36, multi);
    }

    private void numbersMiddleLeft(int multi) {
        addAmount(2, multi);
        addAmount(5, multi);
        addAmount(8, multi);
        addAmount(11, multi);
    }

    private void numbersMiddleMid(int multi) {
        addAmount(14, multi);
        addAmount(17, multi);
        addAmount(20, multi);
        addAmount(23, multi);
    }

    private void numbersMiddleRight(int multi) {
        addAmount(26, multi);
        addAmount(29, multi);
        addAmount(32, multi);
        addAmount(35, multi);
    }

    private void numbersBottomLeft(int multi) {
        addAmount(1, multi);
        addAmount(4, multi);
        addAmount(7, multi);
        addAmount(10, multi);
    }

    private void numbersBottomID(int multi) {
        addAmount(13, multi);
        addAmount(16, multi);
        addAmount(19, multi);
        addAmount(22, multi);
    }

    private void numbersBottomRight(int multi) {
        addAmount(25, multi);
        addAmount(28, multi);
        addAmount(31, multi);
        addAmount(34, multi);
    }
}
