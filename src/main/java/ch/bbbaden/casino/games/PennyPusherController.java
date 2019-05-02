package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class PennyPusherController implements Controller {
    public GridPane field;
    public Label coins;
    public Button btn_slot1;
    public Button btn_slot2;
    public Button btn_slot3;
    public Button btn_push;

    private PennyPusherModel pennyPusherModel;

    private void updateField(int[][] curField) {
        for (int i = 0; i < curField.length; i++) {
            for (int j = 0; j < curField[i].length; j++) {
                if (curField[i][j] == 0) {
                    getFieldLabel(i, j).setText("0");
                } else {
                    getFieldLabel(i, j).setText(String.valueOf(curField[i][j]));
                }
            }
        }
    }

    private Label getFieldLabel(final int row, final int column) {
        Label result = null;
        ObservableList<Label> childrens = FXCollections.observableArrayList();
        for (Node node : field.getChildren()) {
            if (node instanceof Label) {
                childrens.add((Label) node);
            }
        }

        for (Label label : childrens) {
            if (GridPane.getRowIndex(label) == row && GridPane.getColumnIndex(label) == column) {
                result = label;
                break;
            }
        }

        return result;
    }

    public void update() {
        coins.setText(pennyPusherModel.getCoins());
        updateField(pennyPusherModel.getField());
        btn_push.setDisable(pennyPusherModel.isBtn_push_disabled());
        btn_slot1.setDisable(pennyPusherModel.isBtn_slot1_disabled());
        btn_slot2.setDisable(pennyPusherModel.isBtn_slot2_disabled());
        btn_slot3.setDisable(pennyPusherModel.isBtn_slot3_disabled());
    }

    public void initialize(Model model) {
        pennyPusherModel = (PennyPusherModel) model;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 14; j++) {
                Label tempLabel = new Label();
                tempLabel.setPrefWidth(Integer.MAX_VALUE);
                tempLabel.setPrefHeight(Integer.MAX_VALUE);
                tempLabel.setText("0");
                tempLabel.setTextAlignment(TextAlignment.CENTER);
                tempLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-wrap-text:true; -fx-border-color:black;");
                tempLabel.setPadding(new Insets(5));
                tempLabel.setAlignment(Pos.CENTER);
                field.add(tempLabel, j, i);
            }
        }
        update();
    }

    public void btn_slot1_onAction(ActionEvent actionEvent) {
        pennyPusherModel.slot1();
    }

    public void btn_slot2_onAction(ActionEvent actionEvent) {
        pennyPusherModel.slot2();
    }

    public void btn_slot3_onAction(ActionEvent actionEvent) {
        pennyPusherModel.slot3();
    }

    public void btn_push_onAction(ActionEvent actionEvent) {
        pennyPusherModel.push();
    }
}
