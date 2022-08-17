package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.util.*;

public class InitParameters {

    ProcessorParam processor = new ProcessorParam();

    private final Long durationCycle = 3_000L;
    private final int amountCycles = 3;
    private int startAmountAnimals;

    private boolean termForStopping;
    private int startAmountChildren;

    List<Map<Class<? extends Nature>, Integer>> parameters = new ArrayList<>();
    {
        parameters.add(ParamHerbivores.getMapOfAnimals());
        parameters.add(ParamPredators.getMapOfAnimals());
        parameters.add(ParamPlants.getMapOfPlants());
        System.out.println("parameters.size = " + parameters.size());

        initField();

        for (Map<Class<? extends Nature>, Integer> map: parameters) {
            List<Queue<? extends Nature>> listQueuesByObjects = processor.createListQueuesByObjects(map);
            processor.allocateObjsIntoField(listQueuesByObjects);
        }
    }

    public InitParameters(boolean manual) {
        if (manual) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the width of the field");
        }
    }

    private void initField() {
        for (int i = 0; i < Island.getWidthField(); i++) {
            for (int j = 0; j < Island.getHeightField(); j++) {
                Island.getInstance().getField()[i][j] = new Location();
            }
        }
    }

    public static class Dialogue{

    }
}

