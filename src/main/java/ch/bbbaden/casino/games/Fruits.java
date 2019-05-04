package ch.bbbaden.casino.games;

import javafx.scene.image.Image;

import java.io.File;

public class Fruits {

    private String imgPath;
    private int value;

    public Fruits(String imgPath, int value) {
        this.setImage(imgPath);
        this.setValue(value);
    }

    public void setImage(String imgPath) {
        this.imgPath = "/src/main/resources/images/SuperCherry/fruits/"+imgPath+".png";
    }
    public String getImage() {
        return this.imgPath;
    }
    public void setValue(int v) {
        this.value=v;
    }
    public int getValue() {
        return this.value;
    }

}

