package ch.bbbaden.casino.games;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;

public class SlotMachineRow implements Runnable {

    private static int randomNumber;
    private static String urlOfImage;
    private ImageView imageView;

    public SlotMachineRow(ImageView imageView) {
        this.imageView = imageView;
    }
    @Override
    public void run() {
        while (true) {
            Platform.runLater(() -> {
                getRandomFruit();
                //Image image = new Image(getRandomFruit().toURI().toString());
                //ImageView imageView = new ImageView(image);
                //File file = new File(urlOfImage);
                //imageView.setImage(new Image(getRandomFruit().toURI().toString()));
                //imageView = new ImageView(image);
                //label.setGraphic(imageView);
                /*File file = new File(urlOfImage);
                Image image = new Image(file.toURI().toString());*/
                System.out.println(urlOfImage);
            });
        }
}
    public static String getRandomFruit() {
        ArrayList<Fruits> fruitsArray;
        fruitsArray = SlotMachineRow.spin();
        randomNumber = (int) (Math.random() * 9);
        urlOfImage = (fruitsArray.get(randomNumber).getImage());
        return urlOfImage;
    }
    private static ArrayList<Fruits> fruitsArray = new ArrayList<>();

    private static ArrayList<Fruits> spin() {

        fruitsArray.add(new Fruits("STAR", 9));
        fruitsArray.add(new Fruits("BELL", 8));
        fruitsArray.add(new Fruits("CHERRY", 7));
        fruitsArray.add(new Fruits("GRAPES", 6));
        fruitsArray.add(new Fruits("LEMON", 5));
        fruitsArray.add(new Fruits("MELON", 4));
        fruitsArray.add(new Fruits("PEACH", 3));
        fruitsArray.add(new Fruits("POTATO", 2));
        fruitsArray.add(new Fruits("STRAWBERRY", 1));
        return fruitsArray;
    }
    public int getRandomNumber() { return randomNumber; }
    public String getUrlOfImage() { return urlOfImage; }
}
