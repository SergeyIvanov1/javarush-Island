package ru.javarush.sergeyivanov.island.inicialization;

import ru.javarush.sergeyivanov.island.content_of_island.fauna.Animal;
import ru.javarush.sergeyivanov.island.content_of_island.Nature;

import java.util.*;

public class Parameters {

    ProcessorParam processor = new ProcessorParam();

    public static Map<Class<? extends Nature>, Integer> cacheNatureObj;
    public static Map<String, Map<Class<? extends Animal>, Integer>> cacheRations;
    public static Map<String, Map<String, Number>> cacheSettings;

    public Parameters() {
        cacheNatureObj = DBProcessor.getCacheNatureObjectsFromDB();
        System.out.println("Size of Map cacheNatureObj = " + cacheNatureObj.size());
        cacheSettings = DBProcessor.getCacheSettingsFromDataBase();
        cacheRations = DBProcessor.getCacheRationsFromDataBase();
        processor.inputCellsToTheField();
        List<Queue<? extends Nature>> listQueuesByObjects = processor.createListQueuesByObjects(cacheNatureObj);
        processor.allocateObjsIntoField(listQueuesByObjects);
    }

    public void printRations() {
        System.out.println("\n*** RATIONS ***\n");
        for (Map.Entry<String, Map<Class<? extends Animal>, Integer>> entry: cacheRations.entrySet()) {
            System.out.println("===============");
            System.out.println(entry.getKey() + ":");
            for (Map.Entry<Class<? extends Animal>, Integer> entry1: entry.getValue().entrySet()) {
                System.out.println(entry1.getKey().getSimpleName() + " = " + entry1.getValue());
            }
        }
        System.out.println();
    }

    public void printSettings() {
        System.out.println("\n*** SETTINGS ***\n");
        for (Map.Entry<String, Map<String, Number>> entry: cacheSettings.entrySet()) {
            System.out.println("+++++++++++++++");
            System.out.println(entry.getKey());
            for (Map.Entry<String, Number> entry1: entry.getValue().entrySet()) {
                System.out.println(entry1.getKey() + " : " + entry1.getValue());
            }
        }
    }
}
