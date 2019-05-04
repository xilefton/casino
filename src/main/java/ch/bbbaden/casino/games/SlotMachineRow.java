package ch.bbbaden.casino.games;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;

public class SlotMachineRow implements Runnable {

    private String urlOfImage;
    private int randomNumber;
    private Label label;

    public SlotMachineRow(Label label) {
        this.label = label;
    }
    @Override
    public void run() {
        while (true) {
            Platform.runLater(() -> {
                ImageView imageView = new ImageView(getRandomFruit());
                //label.setGraphic(imageView);
                /*File file = new File(urlOfImage);
                Image image = new Image(file.toURI().toString());*/
                System.out.println(urlOfImage);
                //imageView = new ImageView(image);
            });
            try { Thread.sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

}
    public Image getRandomFruit() {
        ArrayList<Fruits> fruitsArray;
        fruitsArray = SlotMachineRow.spin();
        randomNumber = (int) (Math.random() * 9);
        urlOfImage = (fruitsArray.get(randomNumber).getImage());
        File file = new File(urlOfImage);
        Image image = new Image(file.toURI().toString());
        return image;
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
