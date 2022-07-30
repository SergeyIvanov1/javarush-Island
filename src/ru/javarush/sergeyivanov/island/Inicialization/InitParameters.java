package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class InitParameters {

    private static int widthSize = 5;
    private static int heightSize = 5;

    private Long durationCycle = 3_000L;
    private int amountCycles = 3;
    private int startAmountAnimals;

    private boolean termForStopping;
    private int startAmountChildren;

    List<Map<Class, Integer>> parameters = new ArrayList<>();
    {
        parameters.add(ParamHerbivores.getMap());
        parameters.add(ParamPredators.getMap());
        parameters.add(ParamPlants.getMap());

        initField();

        for (Map<Class, Integer> map: parameters) {
            List<BlockingQueue<Object>> listQueuesByObjects = ProcessorInitParam.createListQueuesByObjects(map);
            ProcessorInitParam.allocateObjIntoField(listQueuesByObjects);
        }
    }

    public static int getWidthField() {
        return widthSize;
    }

    public static int getHeightField() {
        return heightSize;
    }

    public InitParameters(boolean manual) {
        if (manual) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the width of the field");
        }
    }

    private void initField() {
        for (int i = 0; i < widthSize; i++) {
            for (int j = 0; j < heightSize; j++) {
                Island.getInstance().getField()[i][j] = new Location();
            }
        }
    }

    public static class Dialogue{

    }
}
