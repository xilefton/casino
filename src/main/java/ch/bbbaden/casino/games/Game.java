package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;
import javafx.scene.image.Image;

public class Game extends Model {
    private NormalUser normalUser;
    private Image image;
    private String name;

    Game(String fxmlDocument, String windowTitle, String imagePath, NormalUser normalUser, String name) {
        super(fxmlDocument, windowTitle, false);
        this.image = new Image(imagePath);
        this.normalUser = normalUser;
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public NormalUser getNormalUser() {
        return normalUser;
    }
}
