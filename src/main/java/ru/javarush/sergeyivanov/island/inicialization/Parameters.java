package ru.javarush.sergeyivanov.island.inicialization;

import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.content_of_island.field.Island;
import ru.javarush.sergeyivanov.island.content_of_island.field.Location;

import java.util.*;

public class Parameters {

    private int amount = 4;
    private Map<Class<? extends Nature>, Integer> cacheNatureObj;
    private Map<String, Map<Class<? extends Nature>, Integer>> cacheRations;
    private Map<String, Map<String, Number>> cacheSettings;
    private final Map<String, List<Class<? extends Nature>>> mapOfListsRation = new HashMap<>();
    private final Island island;
    private final ProcessorParam processor;
    private final SourceParamProcessor dataBaseProcessor;


    public Parameters(Island island) {
        this.island = island;
        this.processor = new ProcessorParam(this);
        this.dataBaseProcessor = new SourceParamProcessor(this);
    }

    public Map<Class<? extends Nature>, Integer> getCacheNatureObj() {
        return cacheNatureObj;
    }

    public Map<String, Map<String, Number>> getCacheSettings() {
        return cacheSettings;
    }

    public ProcessorParam getProcessor() {
        return processor;
    }

    public void fillIsland() {
        fillMapOfListsRation(mapOfListsRation);
        processor.inputCellsToTheField();
        List<Queue<? extends Nature>> listQueuesByObjects = processor.createListQueuesByObjects(cacheNatureObj);
        processor.allocateObjsIntoField(listQueuesByObjects);
    }

    public void fillCachesFromDataBase() {
        cacheNatureObj = dataBaseProcessor.getCacheNatureObjectsFromDB();
        cacheSettings = dataBaseProcessor.getCacheSettingsFromDataBase();
        cacheRations = dataBaseProcessor.getCacheRationsFromDataBase();
    }

    public void fillCachesFromJSON() {
        cacheNatureObj = dataBaseProcessor.getCacheNatureObjectsFromJSON();
        cacheSettings = dataBaseProcessor.getCacheSettingsFromJSON();
        cacheRations = dataBaseProcessor.getCacheRationsFromJSON();
    }

        public void printRations() {
        System.out.println("\n\t*** RATIONS ***\nand PROBABILITY OF EATING \n");
        for (Map.Entry<String, Map<Class<? extends Nature>, Integer>> entry: cacheRations.entrySet()) {
            System.out.println("===============");
            System.out.println(entry.getKey() + ":");
            for (Map.Entry<Class<? extends Nature>, Integer> entry1: entry.getValue().entrySet()) {
                System.out.println(entry1.getKey().getSimpleName() + " = " + entry1.getValue());
            }
        }
        System.out.println();
    }

    public void printSettings() {
        System.out.println("\n*** SETTINGS AND VALUES ***\n");
        for (Map.Entry<String, Map<String, Number>> entry: cacheSettings.entrySet()) {
            System.out.println("+++++++++++++++");
            System.out.println(entry.getKey());
            for (Map.Entry<String, Number> mapOfValues: entry.getValue().entrySet()) {
                System.out.println(mapOfValues.getKey() + " : " + mapOfValues.getValue());
            }
        }
    }

    public void printParametersOfField(){
        System.out.println("\t*** ALLOCATION ANIMALS ***\n\tAND PLANTS TO LOCATIONS \n");
        for (int i = 0; i < island.getWidthOfField(); i++) {
            for (int j = 0; j < island.getHeightOfField(); j++) {
                System.out.println("Location[" + i +"][" + j +"]");

                Location currentLocation = island.getField()[i][j];
                for (Map.Entry<Class<? extends Nature>, Integer> entry : cacheNatureObj.entrySet()) {
                    Class<? extends Nature> classObj = entry.getKey();

                    Queue<? extends Nature> storage = currentLocation.getQueueOfNatureObjects(classObj);
                    int amountAnimals = storage.size();
                    System.out.println("Amount " + classObj.getSimpleName() + " in the queue = " + amountAnimals);
                }
                System.out.println("____________________");
            }
        }
    }

    public Island getIsland() {
        return island;
    }

    public Location[][] getFieldOfIsland() {
        return island.getField();
    }

    public Map<Class<? extends Nature>, Integer> getMapProbabilities(String nameAnimal){
        return cacheRations.get(nameAnimal);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmountCycles() {
        return amount;
    }

    private void fillMapOfListsRation(Map<String, List<Class<? extends Nature>>> mapOfListsRation){
        for (Map.Entry<String, Map<Class<? extends Nature>, Integer>> entry: cacheRations.entrySet()) {
            String nameAnimal = entry.getKey();
            Map<Class<? extends Nature>, Integer> rations = entry.getValue();

            List<Class<? extends Nature>> listRation = new ArrayList<>();
            for (Map.Entry<Class<? extends Nature>, Integer> map: rations.entrySet()) {
                Class<? extends Nature> classNature = map.getKey();
                    listRation.add(classNature);
            }
            mapOfListsRation.put(nameAnimal, listRation);
        }
    }

    public List<Class<? extends Nature>> getListRation(String nameAnimal){
        return mapOfListsRation.get(nameAnimal);
    }

    public Map<String, Map<Class<? extends Nature>, Integer>> getCacheRations() {
        return cacheRations;
    }
}
