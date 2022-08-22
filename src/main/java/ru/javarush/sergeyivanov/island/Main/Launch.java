package ru.javarush.sergeyivanov.island.Main;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.*;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.*;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Map;
import java.util.Properties;

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
        //        Scanner scanner = new Scanner(System.in);

//        System.out.println("Does to run the program manually?");
//        boolean answer = scanner.nextBoolean();

        new InitParameters(false);
        printSettings();
        printRations();
        Statistic.printParametersOfField();

        LiveCycle cycle = new LiveCycle();
        cycle.repeatCycle(3);
//        System.out.println("Grass " + Grass.maxObjInCell);
//        System.out.println("Wolf " + Wolf.maxObjInCell);
//        System.out.println("Boa " + Boa.maxObjInCell);
    }
}
