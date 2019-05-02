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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author felix
 */
public class StartController implements Controller {
    @FXML
    private Pane pane_buttons;
    @FXML
    private ImageView logo;

    private StartModel model;

    /**
     * Initializes the controller class.
     */
    public void initialize(Model model) {
        this.model = (StartModel) model;
        playStartAnimations();
    }

    private void playStartAnimations() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1600), logo);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        TranslateTransition moveUp = new TranslateTransition();
        moveUp.setDuration(Duration.millis(1700));
        moveUp.setFromY(+70);
        moveUp.setToY(0);

        ParallelTransition fadeUp = new ParallelTransition();
        fadeUp.setNode(logo);
        fadeUp.getChildren().addAll(fadeIn, moveUp);

        fadeUp.setOnFinished(actionEvent -> {
            FadeTransition fadeIn1 = new FadeTransition();
            fadeIn1.setDuration(Duration.millis(600));
            fadeIn1.setFromValue(0);
            fadeIn1.setToValue(1);

            TranslateTransition moveUp1 = new TranslateTransition();
            moveUp1.setDuration(Duration.millis(700));
            moveUp1.setFromY(+30);
            moveUp1.setToY(0);

            ParallelTransition fadeUp1 = new ParallelTransition();
            fadeUp1.setNode(pane_buttons);
            fadeUp1.getChildren().addAll(fadeIn1, moveUp1);
            fadeUp1.play();
        });

        fadeUp.play();
    }

    public void update() {

    }

    @FXML
    private void login(ActionEvent event) {
        model.openLogin();
    }

    @FXML
    private void registrieren(ActionEvent event) {
        model.openRegister();
    }

    @FXML
    private void adminLogin(ActionEvent event) {
    }
}
