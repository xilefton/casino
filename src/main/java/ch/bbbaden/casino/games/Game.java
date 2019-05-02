package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Model;
import ch.bbbaden.casino.NormalUser;

public class Game extends Model {

    private String imagePath;
    private NormalUser normalUser;

    Game(String fxmlDocument, String windowTitle, String imagePath, NormalUser normalUser) {
        super(fxmlDocument, windowTitle, false);
        this.imagePath = imagePath;
        this.normalUser = normalUser;
    }

    public String getImagePath() {
        return imagePath;
    }

    public NormalUser getNormalUser() {
        return normalUser;
    }
}
