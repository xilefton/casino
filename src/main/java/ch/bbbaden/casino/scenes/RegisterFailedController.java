package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RegisterFailedController implements Controller {

    public Label errorMessage;
    public Button button_abort;
    public Button button_retry;
    private RegisterFailedModel registerFailedModel;

    public void initialize(Model model) {
        registerFailedModel = (RegisterFailedModel) model;
        update();
    }

    public void update() {
        errorMessage.setText(registerFailedModel.getErrorMessage());
    }

    public void button_abort_onAction(ActionEvent actionEvent) {
        registerFailedModel.setRetry(false);
        registerFailedModel.close();
    }

    public void button_retry_onAction(ActionEvent actionEvent) {
        registerFailedModel.setRetry(true);
        registerFailedModel.close();
    }
}
