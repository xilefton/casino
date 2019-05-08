package ch.bbbaden.casino.games;

import java.util.Iterator;
import java.util.Random;

public class SlotMachineRow implements Iterable<Fruit> {

    private Fruit[] row = new Fruit[]{
            new Fruit("/images/supercherry/fruits/STAR.png", FruitType.STAR),
            new Fruit("/images/supercherry/fruits/BELL.png", FruitType.BELL),
            new Fruit("/images/supercherry/fruits/CHERRY.png", FruitType.CHERRY),
            new Fruit("/images/supercherry/fruits/GRAPES.png", FruitType.GRAPES),
            new Fruit("/images/supercherry/fruits/LEMON.png", FruitType.LEMON),
            new Fruit("/images/supercherry/fruits/MELON.png", FruitType.MELON),
            new Fruit("/images/supercherry/fruits/PEACH.png", FruitType.PEACH),
            new Fruit("/images/supercherry/fruits/POTATO.png", FruitType.POTATO),
            new Fruit("/images/supercherry/fruits/STRAWBERRY.png", FruitType.STRAWBERRY),
    };
    private int iterations = 0;
    private int iterationCounter = 0;
    private int index = 0;
    private int indexOffset;

    public SlotMachineRow() {
        Random random = new Random();
        //Random für 0 - 8
        indexOffset = random.nextInt(9);
        index = indexOffset;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public Fruit getFruit() {

        Fruit returnFruit = row[index];
        index = indexOffset;
        iterationCounter = 0;
        return returnFruit;
    }
    @Override
    public Iterator<ch.bbbaden.casino.games.Fruit> iterator() {
        return new Iterator<ch.bbbaden.casino.games.Fruit>() {

            @Override
            public boolean hasNext() {
                if (iterationCounter < iterations) {
                   return true;
                } else {
                    iterations = 0;
                    iterationCounter = 0;
                    return false;
                }
            }

            @Override
            public Fruit next() {
                Fruit fruit;
                index++;
                if (index >= row.length) {
                    index = 0;
                    fruit = row[index];
                } else {
                    fruit = row[index];
                }
                iterationCounter++;
                return fruit;
            }
        };
    }
}
