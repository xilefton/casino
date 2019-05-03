package ch.bbbaden.casino;

import ch.bbbaden.casino.scenes.ErrorMessageModel;
import ch.bbbaden.casino.scenes.ErrorType;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Model {
    private Stage stage;
    private Controller controller;
    private ModelManager sceneManager;
    private String fxmlDocument, windowTitle;
    private boolean isPrimary;
    private Parent root;

    public Model(String fxmlDocument, String windowTitle, boolean isPrimary) {
        this.fxmlDocument = fxmlDocument;
        this.windowTitle = windowTitle;
        this.isPrimary = isPrimary;
    }

    void loadScene() {
        try {
            if (isPrimary) stage = sceneManager.getStage();
            else stage = sceneManager.getSecondaryStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlDocument));
            root = loader.load();
            root.setOpacity(0);
            FadeTransition fd = new FadeTransition();
            fd.setToValue(1);
            fd.setDuration(Duration.millis(800));
            fd.setNode(root);
            fd.play();
            controller = loader.getController();
            Scene scene = new Scene(root);
            stage.setTitle(windowTitle);
            stage.getIcons().add(new Image("/images/casino_logo.gif"));
            stage.setScene(scene);
            stage.setResizable(false);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    void setSceneManager(ModelManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    protected void showErrorMessage(ErrorMessageModel errorMessageModel) {
        changeScene(errorMessageModel);
    }

    protected void showErrorMessage(String errorMessage, String windowTitle, ErrorType errorType) {
        changeScene(new ErrorMessageModel(errorMessage, windowTitle, errorType));
    }

    protected void show() {
        controller.initialize(this);
        stage.sizeToScene();
        if (isPrimary) stage.show();
        else stage.showAndWait();
    }

    void showAndWait() {
        controller.initialize(this);
        stage.sizeToScene();
        stage.showAndWait();
    }

    protected void changeScene(Model model) {
        if (model.isPrimary()) {
            FadeTransition fd = new FadeTransition();
            fd.setToValue(0);
            fd.setDuration(Duration.millis(800));
            fd.setOnFinished((av) -> sceneManager.setModel(model));
            fd.setNode(root);
            fd.play();
        } else {
            sceneManager.setSecondaryModel(model);
        }
    }

    protected void notifyController() {
        controller.update();
    }

    public void close() {
        stage.close();
    }

    protected void hide() {
        stage.hide();
    }

    private boolean isPrimary() {
        return isPrimary;
    }
}
