package ru.javarush.sergeyivanov.island.main;

import ru.javarush.sergeyivanov.island.content_of_island.fauna.Animal;
import ru.javarush.sergeyivanov.island.content_of_island.field.Island;
import ru.javarush.sergeyivanov.island.content_of_island.field.Location;
import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.inicialization.InitParameters;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistic {
    public static AtomicInteger amountBorn = new AtomicInteger(0);
    public static AtomicInteger amountHungryDeath = new AtomicInteger(0);
    public static AtomicInteger amountDeathsOfOldAge = new AtomicInteger(0);
    public static AtomicInteger amountEatenAnimals = new AtomicInteger(0);
    public static AtomicInteger amountEatenPlants = new AtomicInteger(0);
    private static int amountAnimalsAfterInit;
    private static int amountAnimalsInNewCycle;

    public static void printParametersOfField(){
        for (int i = 0; i < Island.getWidthField(); i++) {
            for (int j = 0; j < Island.getHeightField(); j++) {
                System.out.println("Location[" + i +"][" + j +"]");

                Location currentLocation = Island.getInstance().getField()[i][j];
                for (Map.Entry<Class<? extends Nature>, Integer> entry : InitParameters.cacheNatureObj.entrySet()) {
                    Class<? extends Nature> classObj = entry.getKey();

                    Queue<? extends Nature> storage = currentLocation.getQueueOfNatureObjects(classObj);
                    int amountAnimals = storage.size();
                    System.out.println("Amount " + classObj.getSimpleName() + " in the queue = " + amountAnimals);

                    if (Animal.class.isAssignableFrom(entry.getKey())) {
                        amountAnimalsAfterInit += amountAnimals;
                    }
                }
                System.out.println("____________________");
            }
        }
    }

    public static void printParamCurrentCycle(){
        System.out.println("\n****** Statistic current cycle ******");
        System.out.println("\tAmount animals after initialization = " + amountAnimalsAfterInit);
        System.out.println("\tCommon amount of animals in the end this cycle = " + amountAnimalsInNewCycle);
        System.out.println("\tThe difference between amount animals after initialization " +
                "\n\t\tand common amount of animals in this cycle consists = " +
                (amountAnimalsAfterInit - amountAnimalsInNewCycle));
        System.out.println("\n\tAmount born animals = " + amountBorn);
        System.out.println("\tAmount hungry deaths = " + amountHungryDeath);
        System.out.println("\tAmount deaths of old age = " + amountDeathsOfOldAge);
        System.out.println("\tAmount eaten animals = " + amountEatenAnimals);
        System.out.println("\tAmount eaten plants = " + amountEatenPlants);
        System.out.println();
        amountBorn.set(0);
        amountHungryDeath.set(0);
        amountDeathsOfOldAge.set(0);
        amountEatenAnimals.set(0);
        amountEatenPlants.set(0);
        amountAnimalsInNewCycle = 0;
    }

    public static void calculateAmountAnimals(){
        for (int i = 0; i < Island.getWidthField(); i++) {
            for (int j = 0; j < Island.getHeightField(); j++) {
                Location currentLocation = Island.getInstance().getField()[i][j];

                for (Map.Entry<Class<? extends Nature>, Integer> entry : InitParameters.cacheNatureObj.entrySet()) {
                    Class<? extends Nature> classObj = entry.getKey();
                    Queue<? extends Nature> storage = currentLocation.getQueueOfNatureObjects(classObj);
                    int amountAnimals = storage.size();

                    if (Animal.class.isAssignableFrom(entry.getKey())) {
                        amountAnimalsInNewCycle += amountAnimals;
                    }
                }
            }
        }
    }
}