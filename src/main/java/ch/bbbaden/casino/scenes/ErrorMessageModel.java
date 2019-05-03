package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Model;

public class ErrorMessageModel extends Model {
    private String errorMessage, windowTitle;
    private boolean retry = false;
    private ErrorType errorType;

    public ErrorMessageModel(String errorMessage, String windowTitle, ErrorType errorType) {
        super("/fxml/Error.fxml", "Register failed", false);
        this.errorMessage = errorMessage;
        this.windowTitle = windowTitle;
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public boolean isRetry() {
        return retry;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void showStartScreen() {
        changeScene(new StartModel());
    }
}
