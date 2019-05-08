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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RouletteController implements Controller {
    public Button btn_block;
    public Label anzeige_geldbetrag;
    public ImageView abbrechenimageview;
    public ImageView drehenimageview;
    public ImageView rotaterad;
    public Label labelrndnumber;
    public Label gewinnlabel;
    public Button buttonzero;
    public Button buttonzerozero;
    public AnchorPane anchorPane;
    public ImageView jeton5;
    public ImageView jeton10;
    public ImageView jeton25;
    public ImageView jeton50;
    public ImageView jeton100;
    public Button buttonfirst;
    public Button buttonsecond;
    public Button buttonthird;
    public Button buttonblack;
    public Button button1to18;
    public Button buttoneven;
    public Button buttonred;
    public Button buttonodd;
    public Button button19to36;
    public Button buttonup;
    public Button buttonmid;
    public Button buttondown;
    public Button buttonfive;
    public ImageView ballrotate;
    @FXML
    private GridPane gridPane;
    private RouletteModel rouletteModel;
    private boolean jetonausg = false;
    private boolean spinning = false;
    private int jeton = 0;
    private String image = "";
    private int selectedValue;
    String[] numbers = new String[38];
    ArrayList<Integer> buttonwert = new ArrayList<>();
    ArrayList<ImageView> jetonentf = new ArrayList<>();
    HashMap<Integer, Integer> betrag = new HashMap();
    ArrayList<Integer> chips = new ArrayList<>();

    // Jeton Bilder auf dem Feld per Klick
    private void jetonMaker(Button button, int offset) {
        if(jetonausg) {
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

            try {
                rouletteModel.addCoins(selectedValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            update();
        }
    }

    // Jeton Bilder auf Gridpane setzen
    private void handleButtonAction(Button button, ActionEvent actionEvent){
        jetonMaker(button, 35);
        setWert(button);
    }

    // Jeton Bild auf 0
    public void buttonzero(ActionEvent actionEvent) {
        jetonMaker(buttonzero, 30);
        buttonwert.add(0);
    }

    // Jeton Bild auf 00
    public void buttonzerozero(ActionEvent actionEvent) {
        jetonMaker(buttonzerozero, 30);
        buttonwert.add(100);
    }

    // Geldbetrag anzeigen
    public void update() {
        anzeige_geldbetrag.setText("Ihr Geldbetrag: " + rouletteModel.getCoins());
    }

    public void initialize(Model model) {
        // Array für die Roulette Rad Zahlen
        numbers[0] = "00";
        numbers[1] = "0";
        for(int i = 2; i < 38; i++){
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
        jeton5.setImage(new Image("/images/roulette/Jetons-5-Ausgewählt.png"));
        image = "/images/roulette/Jetons-5.png";
        jeton10.setImage(new Image("/images/roulette/jetons-10.png"));
        jeton25.setImage(new Image("/images/roulette/jetons-25.png"));
        jeton50.setImage(new Image("/images/roulette/jetons-50.png"));
        jeton100.setImage(new Image("/images/roulette/jetons-100.png"));
        jetonausg = true;
        selectedValue = -5;
        jeton = 5;
    }

    public void handleJeton10(MouseEvent mouseEvent) {
        jeton10.setImage(new Image("/images/roulette/Jetons-10-Ausgewählt.png"));
        image = "/images/roulette/Jetons-10.png";
        jeton5.setImage(new Image("/images/roulette/Jetons-5.png"));
        jeton25.setImage(new Image("/images/roulette/jetons-25.png"));
        jeton50.setImage(new Image("/images/roulette/jetons-50.png"));
        jeton100.setImage(new Image("/images/roulette/jetons-100.png"));
        jetonausg = true;
        selectedValue = -10;
        jeton = 10;
    }

    public void handleJeton25(MouseEvent mouseEvent) {
        jeton25.setImage(new Image("/images/roulette/Jetons-25-Ausgewählt.png"));
        image = "/images/roulette/Jetons-25.png";
        jeton5.setImage(new Image("/images/roulette/Jetons-5.png"));
        jeton10.setImage(new Image("/images/roulette/jetons-10.png"));
        jeton50.setImage(new Image("/images/roulette/jetons-50.png"));
        jeton100.setImage(new Image("/images/roulette/jetons-100.png"));
        jetonausg = true;
        selectedValue = -25;
        jeton = 25;
    }

    public void handleJeton50(MouseEvent mouseEvent) {
        jeton50.setImage(new Image("/images/roulette/Jetons-50-Ausgewählt.png"));
        image = "/images/roulette/Jetons-50.png";
        jeton5.setImage(new Image("/images/roulette/Jetons-5.png"));
        jeton10.setImage(new Image("/images/roulette/jetons-10.png"));
        jeton25.setImage(new Image("/images/roulette/jetons-25.png"));
        jeton100.setImage(new Image("/images/roulette/jetons-100.png"));
        jetonausg = true;
        selectedValue = -50;
        jeton = 50;
    }

    public void handleJeton100(MouseEvent mouseEvent) {
        jeton100.setImage(new Image("/images/roulette/Jetons-100-Ausgewählt.png"));
        image = "/images/roulette/Jetons-100.png";
        jeton5.setImage(new Image("/images/roulette/Jetons-5.png"));
        jeton10.setImage(new Image("/images/roulette/jetons-10.png"));
        jeton25.setImage(new Image("/images/roulette/jetons-25.png"));
        jeton50.setImage(new Image("/images/roulette/jetons-50.png"));
        jetonausg = true;
        selectedValue = -100;
        jeton = 100;
    }

    // Rotation des Roulette Rads
    public void handleDrehen(MouseEvent mouseEvent) {
        if(spinning){

        }else {
            gewinnlabel.setText("");
            drehenimageview.setImage(new Image("/images/roulette/drehen_transp.png"));
            ballrotate.setImage(new Image("/images/roulette/rouletterad-ball.png"));
            RotateTransition rt = new RotateTransition(Duration.millis(3000), rotaterad);
            RotateTransition rtball = new RotateTransition(Duration.millis(3000), ballrotate);
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

                ballrotate.setImage(null);

                spinning = false;
                jetonausg = true;


                for (int i = 0; i < jetonentf.size(); i++){
                    anchorPane.getChildren().remove(jetonentf.get(i));
                }

            });

        }
    }

    // Bilder der Buttons anpassen
    public void handledrepressing(MouseEvent mouseEvent) {
        if(spinning){

        }else {
            drehenimageview.setImage(new Image("/images/roulette/drehen_ausg.png"));
        }
    }

    public void handledrerelease(MouseEvent mouseEvent) {
        if(spinning){

        }else {
            drehenimageview.setImage(new Image("/images/roulette/drehen_button.png"));
        }
    }

    public void handlepressing(MouseEvent mouseEvent) {
        abbrechenimageview.setImage(new Image("/images/roulette/abbrechen_ausg.png"));
    }

    // Nummer aus dem Rad generieren
    private void test(){
        Random rnd = new Random();
        int d = rnd.nextInt(38);
        String rndnumber = numbers[d];
        labelrndnumber.setText("Gedrehte Nummer: " + rndnumber);
        if(rndnumber == "00"){
            rndnumber = "100";
        }

        drehenimageview.setImage(new Image("/images/roulette/drehen_button.png"));
        if(betrag.containsKey(Integer.parseInt(rndnumber))){
            gewinn(Integer.parseInt(rndnumber));
        }
        betrag.clear();
    }

    // Gewinnanzeige
    private void gewinn(int d){
        gewinnlabel.setText("Du hast " + betrag.get(d) + " gewonnen!");
        try {
            rouletteModel.addCoins(betrag.get(d));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        update();
    }

    // Werte den Buttons zuordnen
    private void setWert(Button button){
        //Obere Seite
        int column = GridPane.getColumnIndex(button);
        //Linke Seite
        int row = GridPane.getRowIndex(button);
        buttonwert.clear();

        chips.add(jeton);

        switch (row){
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
    private void addBetrag(int i, int multiplicator){
        int d = 0;
        if(betrag.containsKey(i)){
             d = betrag.get(i);
            betrag.remove(i);
        }

        int mult = chips.get(chips.size() - 1) * multiplicator ;
        d += mult;

        betrag.put(i, d);
    }

    private void scColumn(int column, int row, int divide){
        int multiplicatorr = 36;
        if(column % 2 == 1){
            multiplicatorr = 18;
        }

        multiplicatorr /= divide;

        switch (column){
            case 0:
                buttonwert.add(3 - row);
                addBetrag(3 - row, multiplicatorr);
                break;
            case 2:
                buttonwert.add(6 - row);
                addBetrag(6 - row, multiplicatorr);
                break;
            case 4:
                buttonwert.add(9 - row);
                addBetrag(9 - row, multiplicatorr);
                break;
            case 6:
                buttonwert.add(12 - row);
                addBetrag(12 - row, multiplicatorr);
                break;
            case 8:
                buttonwert.add(15 - row);
                addBetrag(15 - row, multiplicatorr);
                break;
            case 10:
                buttonwert.add(18 - row);
                addBetrag(18 - row, multiplicatorr);
                break;
            case 12:
                buttonwert.add(21 - row);
                addBetrag(21 - row, multiplicatorr);
                break;
            case 14:
                buttonwert.add(24 - row);
                addBetrag(24 - row, multiplicatorr);
                break;
            case 16:
                buttonwert.add(27 - row);
                addBetrag(27 - row, multiplicatorr);
                break;
            case 18:
                buttonwert.add(30 - row);
                addBetrag(30 - row, multiplicatorr);
                break;
            case 20:
                buttonwert.add(33 - row);
                addBetrag(33 - row, multiplicatorr);
                break;
            case 22:
                buttonwert.add(36 - row);
                addBetrag(36 - row, multiplicatorr);
                break;
            case 1:
                buttonwert.add(3 - row);
                addBetrag(3 - row, multiplicatorr);
                buttonwert.add(6 - row);
                addBetrag(6 - row, multiplicatorr);
                break;
            case 3:
                buttonwert.add(6 - row);
                addBetrag(6 - row, multiplicatorr);
                buttonwert.add(9 - row);
                addBetrag(9 - row, multiplicatorr);
                break;
            case 5:
                buttonwert.add(9 - row);
                addBetrag(9 - row, multiplicatorr);
                buttonwert.add(12 - row);
                addBetrag(12 - row, multiplicatorr);
                break;
            case 7:
                buttonwert.add(12 - row);
                addBetrag(12 - row, multiplicatorr);
                buttonwert.add(15 - row);
                addBetrag(15 - row, multiplicatorr);
                break;
            case 9:
                buttonwert.add(15 - row);
                addBetrag(15 - row, multiplicatorr);
                buttonwert.add(18 - row);
                addBetrag(18 - row, multiplicatorr);
                break;
            case 11:
                buttonwert.add(18 - row);
                addBetrag(18 - row, multiplicatorr);
                buttonwert.add(21 - row);
                addBetrag(21 - row, multiplicatorr);
                break;
            case 13:
                buttonwert.add(21 - row);
                addBetrag(21 - row, multiplicatorr);
                buttonwert.add(24 - row);
                addBetrag(24 - row, multiplicatorr);
                break;
            case 15:
                buttonwert.add(24 - row);
                addBetrag(24 - row, multiplicatorr);
                buttonwert.add(27 - row);
                addBetrag(27 - row, multiplicatorr);
                break;
            case 17:
                buttonwert.add(27 - row);
                addBetrag(27 - row, multiplicatorr);
                buttonwert.add(30 - row);
                addBetrag(30 - row, multiplicatorr);
                break;
            case 19:
                buttonwert.add(30 - row);
                addBetrag(30 - row, multiplicatorr);
                buttonwert.add(33 - row);
                addBetrag(33 - row, multiplicatorr);
                break;
            case 21:
                buttonwert.add(33 - row);
                addBetrag(33 - row, multiplicatorr);
                buttonwert.add(36 - row);
                addBetrag(36 - row, multiplicatorr);
                break;
    }
}

    // Verschieden Bilder für die Buttons
    public void drehenter(MouseEvent mouseEvent) {
        if(spinning) {

        }else {
            drehenimageview.setImage(new Image("/images/roulette/drehen_ausg.png"));
        }
    }

    public void drehexit(MouseEvent mouseEvent) {
        if(spinning){

        }else {
            drehenimageview.setImage(new Image("/images/roulette/drehen_button.png"));
        }
    }

    public void abbenter(MouseEvent mouseEvent) {
        abbrechenimageview.setImage(new Image("/images/roulette/abbrechen_ausg.png"));
    }


    public void abbexit(MouseEvent mouseEvent) {
        abbrechenimageview.setImage(new Image("/images/roulette/abbrechen_button.png"));
    }

    // Spiel schliesst sich. Zurück auf Startseite/Menü.
    public void abbrechen_onClick(MouseEvent mouseEvent) {
        rouletteModel.close();
    }

    // Die anderen Buttons auf dem Feld
    public void onActionFirst(ActionEvent actionEvent) {
        if(jetonausg) {
            buttonwert.clear();
            jetonMaker(buttonfirst, 40);
            chips.add(jeton);
            numbersUpperleft(3);
            numbersMiddleleft(3);
            numbersBottomleft(3);
        }
    }

    public void onActionSecond(ActionEvent actionEvent) {
        if(jetonausg) {
            buttonwert.clear();
            jetonMaker(buttonsecond, 40);
            chips.add(jeton);
            numbersUppermid(3);
            numbersMiddlemid(3);
            numbersBottommid(3);
        }
    }

    public void onActionThird(ActionEvent actionEvent) {
        if(jetonausg) {
            buttonwert.clear();
            jetonMaker(buttonthird, 40);
            chips.add(jeton);
            numbersUpperright(3);
            numbersMiddleright(3);
            numbersBottomright(3);
        }
    }

    public void onActionBlack(ActionEvent actionEvent) {
        if(jetonausg) {
            buttonwert.clear();
            jetonMaker(buttonblack, 40);
            chips.add(jeton);
            addBetrag(2, 2);
            addBetrag(4, 2);
            addBetrag(6, 2);
            addBetrag(8, 2);
            addBetrag(10, 2);
            addBetrag(11, 2);
            addBetrag(13, 2);
            addBetrag(15, 2);
            addBetrag(17, 2);
            addBetrag(20, 2);
            addBetrag(22, 2);
            addBetrag(24, 2);
            addBetrag(26, 2);
            addBetrag(28, 2);
            addBetrag(29, 2);
            addBetrag(31, 2);
            addBetrag(33, 2);
            addBetrag(35, 2);
        }
    }

    public void onAction1to18(ActionEvent actionEvent) {
        if(jetonausg) {
            buttonwert.clear();
            jetonMaker(button1to18, 40);
            chips.add(jeton);
            numbersUpperleft(2);
            numbersMiddleleft(2);
            numbersBottomleft(2);
            addBetrag(13, 2);
            addBetrag(14, 2);
            addBetrag(15, 2);
            addBetrag(16, 2);
            addBetrag(17, 2);
            addBetrag(18, 2);
        }
    }

    public void onActionEven(ActionEvent actionEvent) {
        if(jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(buttoneven, 40);

            for (int i = 2; i <= 36; i += 2) {
                addBetrag(i, 2);
            }
        }
    }

    public void onActionRed(ActionEvent actionEvent) {
        if(jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(buttonred, 40);
            addBetrag(1, 2);
            addBetrag(3, 2);
            addBetrag(5, 2);
            addBetrag(7, 2);
            addBetrag(9, 2);
            addBetrag(12, 2);
            addBetrag(14, 2);
            addBetrag(16, 2);
            addBetrag(18, 2);
            addBetrag(19, 2);
            addBetrag(21, 2);
            addBetrag(23, 2);
            addBetrag(25, 2);
            addBetrag(27, 2);
            addBetrag(30, 2);
            addBetrag(32, 2);
            addBetrag(34, 2);
            addBetrag(36, 2);
        }
    }

    public void onActionOdd(ActionEvent actionEvent) {
        if(jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(buttonodd, 40);

            for (int i = 1; i <= 35; i += 2) {
                addBetrag(i, 2);
            }
        }
    }

    public void onAction19to36(ActionEvent actionEvent) {
        if(jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(button19to36, 40);
            numbersUpperright(2);
            numbersMiddleright(2);
            numbersBottomright(2);
            addBetrag(19, 2);
            addBetrag(20, 2);
            addBetrag(21, 2);
            addBetrag(22, 2);
            addBetrag(23, 2);
            addBetrag(24, 2);
        }
    }

    public void onActionUp(ActionEvent actionEvent) {
        if(jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(buttonup, 40);
            numbersUpperleft(3);
            numbersUppermid(3);
            numbersUpperright(3);
        }
    }

    public void onActionMid(ActionEvent actionEvent) {
        if(jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(buttonmid, 40);
            numbersMiddleleft(3);
            numbersMiddlemid(3);
            numbersMiddleright(3);
        }
    }

    public void onActionDown(ActionEvent actionEvent) {
        if(jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(buttondown, 40);
            numbersBottomleft(3);
            numbersBottommid(3);
            numbersBottomright(3);
        }
    }

    public void onActionfivenumbers(ActionEvent actionEvent) {
        if(jetonausg) {
            chips.add(jeton);
            buttonwert.clear();
            jetonMaker(buttonfive, 40);
            addBetrag(0, 7);
            addBetrag(100, 7);
            addBetrag(1, 7);
            addBetrag(2, 7);
            addBetrag(3, 7);
        }
    }

    // Funktionen für Zahlfelder
    private void numbersUpperleft(int multi){
        addBetrag(3, multi);
        addBetrag(6, multi);
        addBetrag(9, multi);
        addBetrag(12, multi);
    }

    private void numbersUppermid(int multi){
        addBetrag(15, multi);
        addBetrag(18, multi);
        addBetrag(21, multi);
        addBetrag(24, multi);
    }

    private void numbersUpperright(int multi){
        addBetrag(27, multi);
        addBetrag(30, multi);
        addBetrag(33, multi);
        addBetrag(36, multi);
    }

    private void numbersMiddleleft(int multi){
        addBetrag(2, multi);
        addBetrag(5, multi);
        addBetrag(8, multi);
        addBetrag(11, multi);
    }

    private void numbersMiddlemid(int multi){
        addBetrag(14, multi);
        addBetrag(17, multi);
        addBetrag(20, multi);
        addBetrag(23, multi);
    }
    private void numbersMiddleright(int multi){
        addBetrag(26, multi);
        addBetrag(29, multi);
        addBetrag(32, multi);
        addBetrag(35, multi);
    }

    private void numbersBottomleft(int multi){
        addBetrag(1, multi);
        addBetrag(4, multi);
        addBetrag(7, multi);
        addBetrag(10, multi);
    }

    private void numbersBottommid(int multi){
        addBetrag(13, multi);
        addBetrag(16, multi);
        addBetrag(19, multi);
        addBetrag(22, multi);
    }

    private void numbersBottomright(int multi){
        addBetrag(25, multi);
        addBetrag(28, multi);
        addBetrag(31, multi);
        addBetrag(34, multi);
    }
}
