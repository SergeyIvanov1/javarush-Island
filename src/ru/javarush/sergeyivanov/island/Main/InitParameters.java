package ru.javarush.sergeyivanov.island.Main;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.*;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.*;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InitParameters {

    private static int widthSize = 5;
    private static int heightSize = 5;

    private BlockingQueue<Buffalo> buffaloes;
    private BlockingQueue<Caterpillar> caterpillars;
    private BlockingQueue<Deer> deer;
    private BlockingQueue<Duck> ducks;
    private BlockingQueue<Goat> goats;
    private BlockingQueue<Horse> horses;
    private BlockingQueue<Mouse> mice;
    private BlockingQueue<Rabbit> rabbits;
    private BlockingQueue<Sheep> sheep;
    private BlockingQueue<WildBoar> boars;

    private BlockingQueue<Bear> bears;
    private BlockingQueue<Boa> boas;
    private BlockingQueue<Eagle> eagles;
    private BlockingQueue<Fox> foxes;
    private BlockingQueue<Wolf> wolves;
    private BlockingQueue<Plant> plants;

    private Long durationCycle = 3_000L;
    private int amountCycles = 3;
    private int startAmountAnimals;

    private int buffaloAmount  = 300;
    private int caterpillarAmount = 30_000;
    private int deerAmount  = 600;
    private int duckAmount  = 6_000;
    private int goatAmount  = 4200;
    private int horseAmount = 600;
    private int mouseAmount = 15_000;
    private int rabbitAmount = 4500;
    private int sheepAmount = 4200;
    private int wildBoarAmount = 1500;

    private int bearAmount = 150;
    private int boaAmount = 900;
    private int eagleAmount = 600;
    private int foxAmount = 900;
    private int wolfAmount = 900;

    private boolean termForStopping;
    private int startAmountChildren;

    {
        for (int i = 0; i < buffaloAmount; i++) {
            try {
                buffaloes.put(new Buffalo());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getWidthSize() {
        return widthSize;
    }

    public static int getHeightSize() {
        return heightSize;
    }

    public InitParameters(boolean manual) {
        if (manual) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the width of the field");
            this.buffaloAmount = scanner.nextInt();
            this.caterpillarAmount = scanner.nextInt();
            this.deerAmount = scanner.nextInt();
            this.duckAmount = scanner.nextInt();
            this.goatAmount = scanner.nextInt();
            this.horseAmount = scanner.nextInt();
            this.mouseAmount = scanner.nextInt();
            this.rabbitAmount = scanner.nextInt();
            this.sheepAmount = scanner.nextInt();
            this.wildBoarAmount = scanner.nextInt();

            this.bearAmount = scanner.nextInt();
            this.boaAmount = scanner.nextInt();
            this.eagleAmount = scanner.nextInt();
            this.foxAmount = scanner.nextInt();
            this.wolfAmount = scanner.nextInt();
        }

        for (int i = 0; i < widthSize; i++) {
            for (int j = 0; j < heightSize; j++) {
                Island.getInstance().getField()[i][j] = new Location();
            }
        }

        for (int i = 0; i < widthSize; i++) {
            for (int j = 0; j < heightSize; j++) {
//                Island.getInstance().getField()[i][j].getPredators().offer()
            }
        }
    }

//    private <T> void setAnimalsToQueue(Class<T> animal, LinkedBlockingQueue<Animal> queue, int amountAnimals) throws InstantiationException, IllegalAccessException {
//        for (int i = 0; i < amountAnimals; i++) {
//            try {
//                queue.put((Animal) animal.newInstance());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}

