package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Model;

public class ErrorMessageModel extends Model {
    private String errorMessage, windowTitle;
    private boolean retry = false;
    private ErrorType errorType;
    private Model endModel;

    public ErrorMessageModel(String errorMessage, String windowTitle, Model endModel, ErrorType errorType) {
        super("/fxml/Error.fxml", windowTitle, false);
        this.errorMessage = errorMessage;
        this.windowTitle = windowTitle;
        this.errorType = errorType;
        this.endModel = endModel;
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
        endModel = new StartModel();
        close();
    }

    public Model getEndModel() {
        return endModel;
    }
}
