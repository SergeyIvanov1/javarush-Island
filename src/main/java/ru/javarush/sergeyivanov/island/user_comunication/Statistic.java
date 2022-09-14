package ru.javarush.sergeyivanov.island.user_comunication;

import java.util.concurrent.atomic.AtomicInteger;

public class Statistic {
    public static AtomicInteger amountBorn = new AtomicInteger(0);
    public static AtomicInteger amountHungryDeath = new AtomicInteger(0);
    public static AtomicInteger amountDeathsOfOldAge = new AtomicInteger(0);
    public static AtomicInteger amountEatenAnimals = new AtomicInteger(0);
    public static AtomicInteger amountEatenPlants = new AtomicInteger(0);
    public static int amountAnimalsAfterInit;
    public static int amountAnimalsInNewCycle;

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
}
