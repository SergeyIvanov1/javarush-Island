package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;
import ru.javarush.sergeyivanov.island.Main.DBProcessor;

import java.sql.*;
import java.util.*;

public class InitParameters {

    ProcessorParam processor = new ProcessorParam();

    private final Long durationCycle = 3_000L;
    private final int amountCycles = 3;
    private int startAmountAnimals;

    private boolean termForStopping;
    private int startAmountChildren;
    public static Statement statement;

    public static Map<Class<? extends Nature>, Integer> cacheNatureObj;
    public static Map<String, Map<Class<? extends Animal>, Integer>> cacheRations;
    public static Map<String, Map<String, Number>> cacheSettings;

    {
        cacheNatureObj = DBProcessor.getCacheNatureObjectsFromDB();
        System.out.println("Size of Map cacheNatureObj = " + cacheNatureObj.size());
        cacheSettings = DBProcessor.getCacheSettingsFromDataBase();
        cacheRations = DBProcessor.getCacheRationsFromDataBase();
        initField();
        List<Queue<? extends Nature>> listQueuesByObjects = processor.createListQueuesByObjects(cacheNatureObj);
        processor.allocateObjsIntoField(listQueuesByObjects);
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

