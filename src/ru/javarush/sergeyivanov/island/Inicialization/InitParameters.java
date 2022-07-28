package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.*;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.*;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;

import java.util.Scanner;
import java.util.concurrent.*;

public class InitParameters {

    private static int widthSize = 5;
    private static int heightSize = 5;

    private Long durationCycle = 3_000L;
    private int amountCycles = 3;
    private int startAmountAnimals;

    private boolean termForStopping;
    private int startAmountChildren;

    {
        for (int i = 0; i < widthSize; i++) {
            for (int j = 0; j < heightSize; j++) {
                Island.getInstance().getField()[i][j] = new Location();
            }
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < ParamHerbivores.getBuffaloes().size(); i++) {
            Location randomLocation = Island.getInstance().
                    getField()[random.nextInt(0, widthSize)][random.nextInt(0, heightSize)];
            try {
                randomLocation.getHerbivores().put(ParamHerbivores.getBuffaloes().poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < widthSize; i++) {
            for (int j = 0; j < heightSize; j++) {
                System.out.println(Island.getInstance().getField()[i][j].getHerbivores());
            }
        }
//        System.out.println(randomLocation);
        ExecutorService service = Executors.newWorkStealingPool();

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

        }
    }

}

