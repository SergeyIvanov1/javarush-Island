package ru.javarush.sergeyivanov.island.main;

import ru.javarush.sergeyivanov.island.content_of_island.fauna.Animal;
import ru.javarush.sergeyivanov.island.inicialization.InitParameters;
import java.util.Map;

public class Launch implements Runnable {


    private static void printRations() {
        System.out.println("\n*** RATIONS ***\n");
        for (Map.Entry<String, Map<Class<? extends Animal>, Integer>> entry: InitParameters.cacheRations.entrySet()) {
            System.out.println("===============");
            System.out.println(entry.getKey() + ":");
            for (Map.Entry<Class<? extends Animal>, Integer> entry1: entry.getValue().entrySet()) {
                System.out.println(entry1.getKey().getSimpleName() + " = " + entry1.getValue());
            }
        }
        System.out.println();
    }

    private static void printSettings() {
        System.out.println("\n*** SETTINGS ***\n");
        for (Map.Entry<String, Map<String, Number>> entry: InitParameters.cacheSettings.entrySet()) {
            System.out.println("+++++++++++++++");
            System.out.println(entry.getKey());
            for (Map.Entry<String, Number> entry1: entry.getValue().entrySet()) {
                System.out.println(entry1.getKey() + " : " + entry1.getValue());
            }
        }
    }

    @Override
    public void run() {
        new InitParameters(false);
        printSettings();
        printRations();
        Statistic.printParametersOfField();

        LiveCycle cycle = new LiveCycle();
        cycle.repeatCycle(3);
    }
}