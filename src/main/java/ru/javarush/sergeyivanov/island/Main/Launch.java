package ru.javarush.sergeyivanov.island.Main;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.*;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.*;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Map;

public class Launch {
    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        Scanner scanner = new Scanner(System.in);

//        System.out.println("Does to run the program manually?");
//        boolean answer = scanner.nextBoolean();

        new InitParameters(false);
        Statistic.printParametersOfField();
        printRations();
        printSettings();

        LiveCycle cycle = new LiveCycle();
        cycle.repeatCycle(3);

    }

    private static void printRations() {
        System.out.println("\n*** RATIONS ***\n");
        for (Map.Entry<String, Map<Class<? extends Animal>, Integer>> entry: InitParameters.cacheRations.entrySet()) {
            System.out.println("===============");
            System.out.println(entry.getKey() + ":");
            for (Map.Entry<Class<? extends Animal>, Integer> entry1: entry.getValue().entrySet()) {
                System.out.println(entry1.getKey().getSimpleName() + " = " + entry1.getValue());
            }
        }
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

    private static void extracted(Animal animal) {
        System.out.println(animal.getClass().getSimpleName() + ":");
        for (Map.Entry<Class<? extends Animal>, Integer> ration: animal.getRation().entrySet()) {
            Class<? extends Nature> key = ration.getKey();
            Integer value = ration.getValue();
            System.out.println(key.getSimpleName() + " = " + value);
        }
        System.out.println();
    }
}
