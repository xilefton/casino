package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;


public class PennyPusherController implements Controller {
    public GridPane field;
    public Label coins;
    public Button btn_slot1;
    public Button btn_slot2;
    public Button btn_slot3;
    public Button btn_push;
    public AnchorPane anchorPane;

    private Image[] coinImages = new Image[]{
            new Image("/images/pennypusher/coin_none.png"), new Image("/images/pennypusher/coin_stack1.png"),
            new Image("/images/pennypusher/coin_stack2.png"), new Image("/images/pennypusher/coin_stack3.png"),
            new Image("/images/pennypusher/coin_stack4.png"), new Image("/images/pennypusher/coin_stack5.png"),
            new Image("/images/pennypusher/coin_stack6.png"), new Image("/images/pennypusher/coin_stack7.png"),
            new Image("/images/pennypusher/coin_stack8.png")};

    private PennyPusherModel pennyPusherModel;

    private void updateField(int[][] curField) {
        for (int i = 0; i < curField.length; i++) {
            for (int j = 0; j < curField[i].length; j++) {
                if (curField[i][j] <= coinImages.length - 1) {
                    getFieldImageView(i, j).setImage(coinImages[curField[i][j]]);
                } else {
                    getFieldImageView(i, j).setImage(coinImages[coinImages.length - 1]);
                }
            }
        }
    }

    private ImageView getFieldImageView(final int row, final int column) {
        ImageView result = null;
        ObservableList<ImageView> childrens = FXCollections.observableArrayList();
        for (Node node : field.getChildren()) {
            if (node instanceof ImageView) {
                childrens.add((ImageView) node);
            }
        }

        for (javafx.scene.image.ImageView label : childrens) {
            if (GridPane.getRowIndex(label) == row && GridPane.getColumnIndex(label) == column) {
                result = label;
                break;
            }
        }

        return result;
    }

    public void update() {
        // coins.setText(pennyPusherModel.getCoins());
        animateFieldChanges();
        updateField(pennyPusherModel.getField());
        btn_push.setDisable(pennyPusherModel.isBtn_push_disabled());
        btn_slot1.setDisable(pennyPusherModel.isBtn_slot1_disabled());
        btn_slot2.setDisable(pennyPusherModel.isBtn_slot2_disabled());
        btn_slot3.setDisable(pennyPusherModel.isBtn_slot3_disabled());
    }

    private void animateFieldChanges() {
        ParallelTransition mainTransition = new ParallelTransition();

        ImageView imageView1 = getFieldImageView(0, 1);
        Bounds bounds1 = imageView1.getBoundsInLocal();
        Bounds screenBounds1 = imageView1.localToScreen(bounds1);

        ImageView imageView2 = getFieldImageView(0, 1);
        Bounds bounds2 = imageView2.getBoundsInLocal();
        Bounds screenBounds2 = imageView2.localToScreen(bounds2);

        final double rowOffset = screenBounds1.getMinY() - screenBounds2.getMinY();
        final double columnOffset = screenBounds1.getMinX() - screenBounds2.getMinX();

        for (FieldChange fieldChange : pennyPusherModel.getFieldChanges()) {
            ImageView coin = new ImageView(coinImages[1]);
            anchorPane.getChildren().add(coin);

            FadeTransition coinFade = new FadeTransition();
            coinFade.setToValue(1);
            coinFade.setFromValue(0);
            coinFade.setAutoReverse(true);
            coinFade.setDuration(Duration.millis(1200));

            ImageView startImageView = getFieldImageView(fieldChange.getStartY(), fieldChange.getStartX());
            Bounds startBounds = startImageView.getBoundsInLocal();
            Bounds startScreenBounds = startImageView.localToScreen(startBounds);

            TranslateTransition coinMove = new TranslateTransition();
            coinMove.setFromX(startScreenBounds.getMinX());
            coinMove.setFromY(startScreenBounds.getMinY());
            if (fieldChange.getStartX() < fieldChange.getEndX())
                coinMove.setToX(startScreenBounds.getMinX() + columnOffset);
            else coinMove.setToX(startScreenBounds.getMinX() - columnOffset);
            if (fieldChange.getStartY() < fieldChange.getEndY())
                coinMove.setToY(startScreenBounds.getMinY() + rowOffset);
            else coinMove.setToY(startScreenBounds.getMinY() - rowOffset);

            coinMove.setDuration(Duration.millis(2400));

            ParallelTransition coinAnimation = new ParallelTransition();
            coinAnimation.getChildren().addAll(coinFade, coinMove);
            coinAnimation.setNode(coin);
            mainTransition.getChildren().add(coinAnimation);
        }
        mainTransition.setOnFinished((ae) -> pennyPusherModel.getFieldChanges().clear());
        mainTransition.play();
    }

    public void initialize(Model model) {
        pennyPusherModel = (PennyPusherModel) model;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 13; j++) {
                ImageView imageView = new ImageView();
                imageView.setFitHeight(58);
                imageView.setFitWidth(58);
                imageView.autosize();
                field.add(imageView, j, i);
            }
        }
        update();
    }

    public void btn_slot1_onAction() {
        pennyPusherModel.slot1();
    }

    public void btn_slot2_onAction() {
        pennyPusherModel.slot2();
    }

    public void btn_slot3_onAction() {
        pennyPusherModel.slot3();
    }

    public void btn_push_onAction() {
        pennyPusherModel.push();
    }
}
