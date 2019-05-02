/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author felix
 */
public class HomeController implements Controller {
    @FXML
    private Label coins;
    @FXML
    private ImageView game_image;
    @FXML
    private Pane gameView;

    private HomeModel homeModel;

    public void initialize(Model model) {
        homeModel = (HomeModel) model;
        update();
    }

    public void update() {
        coins.setText(homeModel.getCoins());
        homeModel.getPurchasedCoins();
        game_image.setImage(new Image(homeModel.getImagePath()));
    }

    public void btn_play_onAction(ActionEvent actionEvent) {
        homeModel.playGame();
    }

    public void btn_right_onAction(ActionEvent actionEvent) {
        fade(20, 0, 1, 0);
        homeModel.changeGame(1);
        fade(0, -20, 0, 1);
    }

    public void btn_left_onAction(ActionEvent actionEvent) {
        fade(-20, 0, 1, 0);
        homeModel.changeGame(-1);
        fade(0, 20, 0, 1);
    }

    private void fade(int moveTo, int moveFrom, float fadeFrom, float fadeTo) {
        FadeTransition fadeTransition =
                new FadeTransition(Duration.millis(500), gameView);
        fadeTransition.setFromValue(fadeFrom);
        fadeTransition.setToValue(fadeTo);
        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(490), gameView);
        translateTransition.setFromX(moveFrom);
        translateTransition.setToX(moveTo);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                fadeTransition,
                translateTransition
        );
        parallelTransition.play();
    }
}
