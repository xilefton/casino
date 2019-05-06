package ch.bbbaden.casino.games;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;

public class SlotMachineRow implements Runnable {

    private static String urlOfImage;
    private ImageView imageView;

    public SlotMachineRow(ImageView imageView) {
        this.imageView = imageView;
    }
    @Override
    public void run() {
        while (true) {
            Platform.runLater(() -> {
                imageView.setImage(new Image(getRandomFruit()));
            });
            try { Thread.sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static String getRandomFruit() {
        ArrayList<Fruits> fruitsArray;
        fruitsArray = SlotMachineRow.spin();
        int randomNumber = (int) (Math.random() * 9);
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
    public String getUrlOfImage() { return urlOfImage; }
}
