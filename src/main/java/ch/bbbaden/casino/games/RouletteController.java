package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.animation.Animation;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class RouletteController implements Controller {

    public ImageView jeton100;
    public Button btn_block;
    public Label anzeige_geldbetrag;
    public ImageView abbrechenimageview;
    public ImageView drehenimageview;
    public ImageView entfernenimageview;
    public ImageView roulettespinner;
    public ImageView rotaterad;
    SelectedJeton selectedJeton;

    public AnchorPane anchorPane;
    public ImageView jeton5;
    public ImageView jeton25;
    public ImageView jeton50;
    public ImageView jeton10;
    private RouletteModel rouletteModel;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    @FXML
    private GridPane gridPane;
    private boolean jetonausg = false;
    private String image = "";

    private void handleButtonAction(Button button, ActionEvent actionEvent) {
        if(jetonausg) {
            System.out.println(button);
            ImageView iv = new ImageView(image);
            System.out.println(GridPane.getColumnIndex(button));
            System.out.println(GridPane.getRowIndex(button));
            anchorPane.getChildren().add(iv);
            double nodeMinX = button.getLayoutBounds().getMinX();
            double nodeMinY = button.getLayoutBounds().getMinY();
            Point2D nodeInScene = button.localToScene(nodeMinX, nodeMinY);

            double gridPaneMinX = button.getLayoutBounds().getMinX();
            double gridPaneMinY = button.getLayoutBounds().getMinY();
            Point2D gridPaneInScene = button.localToScene(gridPaneMinX, gridPaneMinY);

            int offset = 30;

            iv.relocate(nodeInScene.getX() - 10
                    + iv.getLayoutBounds().getMinX() - 49, nodeInScene.getY() - offset
                    + iv.getLayoutBounds().getMinY() - offset);
            iv.setScaleX(0.4);
            iv.setScaleY(0.4);
        }
    }

    public void update() {
        anzeige_geldbetrag.setText("Ihr Geldbetrag: " + rouletteModel.getCoins());
    }

    public void initialize(Model model) {

        btn_block.setShape(new Circle(10));

        rouletteModel = (RouletteModel) model;

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


    public void handleJeton5(MouseEvent mouseEvent) {
        jeton5.setImage(new Image("/images/Roulette_Bilder/Jetons-5-Ausgewählt.png"));
        image = "/images/Roulette_Bilder/Jetons-5.png";
        jeton10.setImage(new Image("/images/Roulette_Bilder/jetons-10.png"));
        jeton25.setImage(new Image("/images/Roulette_Bilder/jetons-25.png"));
        jeton50.setImage(new Image("/images/Roulette_Bilder/jetons-50.png"));
        jeton100.setImage(new Image("/images/Roulette_Bilder/jetons-100.png"));
        jetonausg = true;
    }

    public void handleJeton10(MouseEvent mouseEvent) {
        jeton10.setImage(new Image("/images/Roulette_Bilder/Jetons-10-Ausgewählt.png"));
        image = "/images/Roulette_Bilder/Jetons-10.png";
        jeton5.setImage(new Image("/images/Roulette_Bilder/Jetons-5.png"));
        jeton25.setImage(new Image("/images/Roulette_Bilder/jetons-25.png"));
        jeton50.setImage(new Image("/images/Roulette_Bilder/jetons-50.png"));
        jeton100.setImage(new Image("/images/Roulette_Bilder/jetons-100.png"));
        jetonausg = true;
    }

    public void handleJeton25(MouseEvent mouseEvent) {
        jeton25.setImage(new Image("/images/Roulette_Bilder/Jetons-25-Ausgewählt.png"));
        image = "/images/Roulette_Bilder/Jetons-25.png";
        jeton5.setImage(new Image("/images/Roulette_Bilder/Jetons-5.png"));
        jeton10.setImage(new Image("/images/Roulette_Bilder/jetons-10.png"));
        jeton50.setImage(new Image("/images/Roulette_Bilder/jetons-50.png"));
        jeton100.setImage(new Image("/images/Roulette_Bilder/jetons-100.png"));
        jetonausg = true;
    }

    public void handleJeton50(MouseEvent mouseEvent) {
        jeton50.setImage(new Image("/images/Roulette_Bilder/Jetons-50-Ausgewählt.png"));
        image = "/images/Roulette_Bilder/Jetons-50.png";
        jeton5.setImage(new Image("/images/Roulette_Bilder/Jetons-5.png"));
        jeton10.setImage(new Image("/images/Roulette_Bilder/jetons-10.png"));
        jeton25.setImage(new Image("/images/Roulette_Bilder/jetons-25.png"));
        jeton100.setImage(new Image("/images/Roulette_Bilder/jetons-100.png"));
        jetonausg = true;
    }

    public void handleJeton100(MouseEvent mouseEvent) {
        jeton100.setImage(new Image("/images/Roulette_Bilder/Jetons-100-Ausgewählt.png"));
        image = "/images/Roulette_Bilder/Jetons-100.png";
        jeton5.setImage(new Image("/images/Roulette_Bilder/Jetons-5.png"));
        jeton10.setImage(new Image("/images/Roulette_Bilder/jetons-10.png"));
        jeton25.setImage(new Image("/images/Roulette_Bilder/jetons-25.png"));
        jeton50.setImage(new Image("/images/Roulette_Bilder/jetons-50.png"));
        jetonausg = true;
    }

    public void abbrechen_onClick(MouseEvent mouseEvent) {
        rouletteModel.close();
    }

    public void handlepressing(MouseEvent mouseEvent) {
        abbrechenimageview.setImage(new Image("/images/Roulette_Bilder/abbrechen-ausg.png"));
    }

    public void handleDrehen(MouseEvent mouseEvent) {
        RotateTransition rt = new RotateTransition(Duration.millis(3000), rotaterad);
        rt.setByAngle(360);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }

    public void handledrepressing(MouseEvent mouseEvent) {
        drehenimageview.setImage(new Image("/images/Roulette_Bilder/drehen-ausg.png"));
    }

    public void handleEntfernen(MouseEvent mouseEvent) {
    }

    public void handleentpressing(MouseEvent mouseEvent) {
        entfernenimageview.setImage(new Image("/images/Roulette_Bilder/entfernen-ausg.png"));
    }

    public void handledrerelease(MouseEvent mouseEvent) {
        drehenimageview.setImage(new Image("/images/Roulette_Bilder/drehen-button.png"));
    }

    public void handleentrelease(MouseEvent mouseEvent) {
        entfernenimageview.setImage(new Image("/images/Roulette_Bilder/entfernen-button.png"));
    }

    public void handlerelease(MouseEvent mouseEvent) {
    }
}
