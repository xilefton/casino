package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;

public class ErrorMessageController implements Controller {
    public ButtonBar buttonBar;
    public Label errorMessage;

    private ErrorMessageModel errorMessageModel;

    @Override
    public void update() {
        errorMessage.setText(errorMessageModel.getErrorMessage());
    }

    @Override
    public void initialize(Model model) {
        errorMessageModel = (ErrorMessageModel) model;

        Button[] buttons;

        switch (errorMessageModel.getErrorType()) {
            case CRITICAL:
                buttons = new Button[]{new Button("ok")};
                buttons[0].setOnAction((ActionEvent) -> System.exit(0));
                break;
            case CONNECTION:
                buttons = new Button[]{new Button("ok")};
                buttons[0].setOnAction((ActionEvent) -> errorMessageModel.showStartScreen());
                break;
            case NOTIFICATION:
            default:
                buttons = new Button[]{new Button("ok")};
                buttons[0].setOnAction((ActionEvent) -> errorMessageModel.close());
        }

        buttonBar.getButtons().addAll(buttons);

        update();
    }
}
