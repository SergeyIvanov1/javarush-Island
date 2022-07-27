package ru.javarush.sergeyivanov.island.Main;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InitParameters {

    private static int widthSize;
    private static int heightSize;

    private BlockingQueue<Predator> predators;
    private BlockingQueue<Herbivore> herbivores;
    private BlockingQueue<Plant> plants;
    {
        predators = new LinkedBlockingQueue<>();
        herbivores = new LinkedBlockingQueue<>();
        plants = new LinkedBlockingQueue<>();
    }
    private Long durationCycle;
    private int amountCycles;
    private int startAmountAnimals;

    private int buffaloAmount;
    private int caterpillarAmount;
    private int deerAmount;
    private int duckAmount;
    private int goatAmount;
    private int horseAmount;
    private int mouseAmount;
    private int rabbitAmount;
    private int sheepAmount;
    private int wildBoarAmount;

    private int bearAmount;
    private int boaAmount;
    private int eagleAmount;
    private int foxAmount;
    private int wolfAmount;

    private boolean termForStopping;
    private int startAmountChildren = 0;

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

        } else {
            this.widthSize = 5;
            this.heightSize = 5;

            this.durationCycle = 3_000L;
            this.amountCycles = 3;

            this.buffaloAmount = 300;
            this.caterpillarAmount = 30_000;
            this.deerAmount = 600;
            this.duckAmount = 6_000;
            this.goatAmount = 4200;
            this.horseAmount = 600;
            this.mouseAmount = 15_000;
            this.rabbitAmount = 4500;
            this.sheepAmount = 4200;
            this.wildBoarAmount = 1500;

            this.bearAmount = 150;
            this.boaAmount = 900;
            this.eagleAmount = 600;
            this.foxAmount = 900;
            this.wolfAmount = 900;
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
}

