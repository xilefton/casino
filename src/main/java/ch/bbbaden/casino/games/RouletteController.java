package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;
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

    public ImageView jeton100;
    public Button btn_block;
    public Label anzeige_geldbetrag;
    public ImageView abbrechenimageview;
    public ImageView drehenimageview;
    public ImageView entfernenimageview;
    public ImageView rotaterad;
    public Label labelrndnumber;
    public Label gewinnlabel;
    public Button buttonzero;
    public Button buttonzerozero;
    public AnchorPane anchorPane;
    public ImageView jeton5;
    public ImageView jeton25;
    public ImageView jeton50;
    public ImageView jeton10;
    private RouletteModel rouletteModel;
    @FXML
    private GridPane gridPane;
    private boolean jetonausg = false;
    private String image = "";
    private int selectedValue;
    String[] numbers = new String[38];
    ArrayList<Integer> buttonwert = new ArrayList<>();
    private boolean spinning = false;
    private int jeton = 0;
    ArrayList<ImageView> jetonentf = new ArrayList<>();

    // Jeton Bilder auf Gridpane setzen
    private void handleButtonAction(Button button, ActionEvent actionEvent){
        if(jetonausg) {
            ImageView iv = new ImageView(image);
            anchorPane.getChildren().add(iv);
            jetonentf.add(iv);
            double nodeMinX = button.getLayoutBounds().getMinX();
            double nodeMinY = button.getLayoutBounds().getMinY();
            Point2D nodeInScene = button.localToScene(nodeMinX, nodeMinY);

            int offset = 30;

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
            setWert(button);
        }
    }

    // Jeton Bild auf 0
    public void buttonzero(ActionEvent actionEvent) {
        if(jetonausg) {
            buttonwert.clear();
            ImageView iv = new ImageView(image);
            anchorPane.getChildren().add(iv);
            double nodeMinX = buttonzero.getLayoutBounds().getMinX();
            double nodeMinY = buttonzero.getLayoutBounds().getMinY();
            Point2D nodeInScene = buttonzero.localToScene(nodeMinX, nodeMinY);

            int offset = 30;

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
            buttonwert.add(0);

            System.out.println(buttonwert);
        }
    }

    // Jeton Bild auf 00
    public void buttonzerozero(ActionEvent actionEvent) {
        if(jetonausg) {
            buttonwert.clear();
            ImageView iv = new ImageView(image);
            anchorPane.getChildren().add(iv);
            double nodeMinX = buttonzero.getLayoutBounds().getMinX();
            double nodeMinY = buttonzero.getLayoutBounds().getMinY();
            Point2D nodeInScene = buttonzero.localToScene(nodeMinX, nodeMinY);

            int offset = 70;

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
            buttonwert.add(420);

            System.out.println(buttonwert);
        }

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

        btn_block.setShape(new Circle(10));
        rouletteModel = (RouletteModel) model;

        //Buttons im GridPane generieren
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
            drehenimageview.setImage(new Image("/images/roulette/drehen-transp.png"));
            RotateTransition rt = new RotateTransition(Duration.millis(3000), rotaterad);
            rt.setByAngle(750);
            rt.setInterpolator(Interpolator.LINEAR);
            rt.play();
            spinning = true;

            rt.setOnFinished(event -> {
                test();
                spinning = false;

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
            drehenimageview.setImage(new Image("/images/roulette/drehen-ausg.png"));
        }
    }

    public void handleentpressing(MouseEvent mouseEvent) {
        entfernenimageview.setImage(new Image("/images/roulette/entfernen-ausg.png"));
    }

    public void handledrerelease(MouseEvent mouseEvent) {
        if(spinning){

        }else {
            drehenimageview.setImage(new Image("/images/roulette/drehen-button.png"));
        }
    }

    public void handleentrelease(MouseEvent mouseEvent) {
        entfernenimageview.setImage(new Image("/images/roulette/entfernen-button.png"));
    }

    public void handlepressing(MouseEvent mouseEvent) {
        abbrechenimageview.setImage(new Image("/images/roulette/abbrechen-ausg.png"));
    }

    // Nummer aus dem Rad generieren
    private void test(){
        Random rnd = new Random();
        int d = rnd.nextInt(38);
        String rndnumber = numbers[d];
        labelrndnumber.setText("Gedrehte Nummer: " + rndnumber);
        drehenimageview.setImage(new Image("/images/roulette/drehen-button.png"));

        if(betrag.containsKey(d)){
            gewinn(d);
        }
        betrag.clear();
    }

    private void gewinn(int d){
        gewinnlabel.setText("Du hast " + betrag.get(d) + " gewonnen!");
        try {
           // rouletteModel.getNormalUser().addCoins(betrag.get(d), false);
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
            case 6:
                break;
            case 7:
                break;
        }


        System.out.println(buttonwert);
    }
    HashMap<Integer, Integer> betrag = new HashMap();


    ArrayList<Integer> chips = new ArrayList<>();

    // 36 18 12 9 7 3 2
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

    public void drehenter(MouseEvent mouseEvent) {
        drehenimageview.setImage(new Image("/images/roulette/drehen-ausg.png"));
    }

    public void drehexit(MouseEvent mouseEvent) {
        drehenimageview.setImage(new Image("/images/roulette/drehen-button.png"));
    }

    public void entfenter(MouseEvent mouseEvent) {
        entfernenimageview.setImage(new Image("/images/roulette/entfernen-ausg.png"));
    }

    public void entfexit(MouseEvent mouseEvent) {
        entfernenimageview.setImage(new Image("/images/roulette/entfernen-button.png"));
    }

    public void abbenter(MouseEvent mouseEvent) {
        abbrechenimageview.setImage(new Image("/images/roulette/abbrechen-ausg.png"));
    }


    public void abbexit(MouseEvent mouseEvent) {
        abbrechenimageview.setImage(new Image("/images/roulette/abbrechen-button.png"));
    }

    public void abbrechen_onClick(MouseEvent mouseEvent) {
        rouletteModel.close();
    }

    public void onActionFirst(ActionEvent actionEvent) {
    }

    public void onActionSecond(ActionEvent actionEvent) {
    }

    public void onActionThird(ActionEvent actionEvent) {
    }

    public void onActionBlack(ActionEvent actionEvent) {
    }

    public void onAction1to18(ActionEvent actionEvent) {
    }

    public void onActionEven(ActionEvent actionEvent) {
    }

    public void onActionRed(ActionEvent actionEvent) {
    }

    public void onActionOdd(ActionEvent actionEvent) {
    }

    public void onAction19to36(ActionEvent actionEvent) {
    }

    public void onActionUp(ActionEvent actionEvent) {
    }

    public void onActionMid(ActionEvent actionEvent) {
    }

    public void onActionDown(ActionEvent actionEvent) {
    }
}
