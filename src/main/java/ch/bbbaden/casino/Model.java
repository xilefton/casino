package ch.bbbaden.casino;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Model {
    private Stage stage;
    private Controller controller;
    private ModelManager sceneManager;
    private String fxmlDocument, windowTitle;
    private boolean isPrimary;

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
            Parent root = loader.load();
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

    protected void show() {
        controller.initialize(this);
        stage.sizeToScene();
        if (isPrimary) stage.show();
        else stage.showAndWait();
    }

    void showAndWait() {
        controller.initialize(this);
        stage.showAndWait();
    }

    protected void changeScene(Model model) {
        if (model.isPrimary()) {
            sceneManager.setModel(model);
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
