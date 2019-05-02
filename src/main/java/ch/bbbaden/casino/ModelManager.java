package ch.bbbaden.casino;

import javafx.stage.Stage;

class ModelManager {

    private Stage stage;
    private Stage secondaryStage;

    ModelManager(Stage stage) {
        this.stage = stage;
    }

    void setModel(Model model) {
        model.setSceneManager(this);
        model.loadScene();
        model.show();
    }

    void setSecondaryModel(Model model) {
        secondaryStage = new Stage();
        model.setSceneManager(this);
        model.loadScene();
        model.showAndWait();
    }

    Stage getStage() {
        return stage;
    }

    Stage getSecondaryStage() {
        if (secondaryStage == null) secondaryStage = new Stage();
        return secondaryStage;
    }
}
