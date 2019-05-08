package ch.bbbaden.casino.games;

import javafx.scene.image.Image;

public class Fruit {

    private Image image;
    private int value;
    FruitType fruitType;

    public Fruit(String imgPath, FruitType fruitType) {
        image = new Image(imgPath);
        this.fruitType = fruitType;
    }
    public FruitType getFruitType() {
        return fruitType;
    }


    public Image getImage() {
        return image;
    }
}

