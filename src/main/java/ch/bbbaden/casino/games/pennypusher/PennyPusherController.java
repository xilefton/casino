package ch.bbbaden.casino.games.pennypusher;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.animation.Animation;
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
    public Label label_coins;
    public Button btn_slot1;
    public Button btn_slot2;
    public Button btn_slot3;
    public Button btn_push;
    public AnchorPane anchorPane;
    public Label label_profit;

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
        ObservableList<ImageView> children = FXCollections.observableArrayList();
        for (Node node : field.getChildren()) {
            if (node instanceof ImageView) {
                children.add((ImageView) node);
            }
        }

        for (javafx.scene.image.ImageView label : children) {
            if (GridPane.getRowIndex(label) == row && GridPane.getColumnIndex(label) == column) {
                result = label;
                break;
            }
        }

        return result;
    }

    public void update() {
        label_coins.setText(pennyPusherModel.getCoins());
        if (!pennyPusherModel.getFieldChanges().isEmpty()) animateChanges();
        else updateField(pennyPusherModel.getField());
        label_profit.setText(Long.toString(pennyPusherModel.getRoundProfit()));
        btn_push.setDisable(pennyPusherModel.isBtn_push_disabled());
        btn_slot1.setDisable(pennyPusherModel.isBtn_slot1_disabled());
        btn_slot2.setDisable(pennyPusherModel.isBtn_slot2_disabled());
        btn_slot3.setDisable(pennyPusherModel.isBtn_slot3_disabled());
    }

    private void animateChanges() {

        final double rowOffset = 42;
        final double columnOffset = 42;
        ParallelTransition mainTransition = new ParallelTransition();

        for (FieldChange fieldChange : pennyPusherModel.getFieldChanges()) {
            ImageView coin = new ImageView(coinImages[1]);
            anchorPane.getChildren().add(coin);
            coin.setScaleX(0.3);
            coin.setScaleY(0.3);

            FadeTransition coinFadeIn = new FadeTransition();
            coinFadeIn.setToValue(1);
            coinFadeIn.setFromValue(0);
            coinFadeIn.setDuration(Duration.millis(400));

            FadeTransition coinFadeOut = new FadeTransition();
            coinFadeOut.setToValue(0);
            coinFadeOut.setFromValue(1);
            coinFadeOut.setDelay(Duration.millis(400));
            coinFadeOut.setDuration(Duration.millis(400));

            ImageView startImageView = getFieldImageView(fieldChange.getStartY(), fieldChange.getStartX());
            Bounds startBounds = startImageView.getBoundsInLocal();
            Bounds startScreenBounds = startImageView.localToScreen(startBounds);

            TranslateTransition coinMove = new TranslateTransition();
            coinMove.setFromX(startScreenBounds.getMinX() - 520);
            coinMove.setFromY(startScreenBounds.getMinY() - 275);

            if (fieldChange.getStartX() < fieldChange.getEndX())
                coinMove.setToX(startScreenBounds.getMinX() - 520 + columnOffset);
            else if (fieldChange.getStartX() > fieldChange.getEndX())
                coinMove.setToX(startScreenBounds.getMinX() - 520 - columnOffset);
            else coinMove.setToX(startScreenBounds.getMinX() - 520);

            if (fieldChange.getStartY() < fieldChange.getEndY())
                coinMove.setToY(startScreenBounds.getMinY() - 275 + rowOffset);
            else if (fieldChange.getStartY() > fieldChange.getEndY())
                coinMove.setToY(startScreenBounds.getMinY() - 275 - rowOffset);
            else coinMove.setToY(startScreenBounds.getMinY() - 275);

            coinMove.setDuration(Duration.millis(800));

            ParallelTransition coinAnimation = new ParallelTransition();
            coinAnimation.getChildren().addAll(coinFadeIn, coinFadeOut, coinMove);
            coinAnimation.setNode(coin);
            mainTransition.getChildren().add(coinAnimation);
        }

        mainTransition.setOnFinished((ae) -> {
            for (Animation animation : mainTransition.getChildren()) {
                anchorPane.getChildren().remove(((ParallelTransition) animation).getNode());
            }
            updateField(pennyPusherModel.getField());
        });

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
        updateField(pennyPusherModel.getField());
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
