package ru.javarush.sergeyivanov.island.Main;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.*;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Bear;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class Launch {
    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        Scanner scanner = new Scanner(System.in);

//        System.out.println("Does to run the program manually?");
//        boolean answer = scanner.nextBoolean();

        new InitParameters(false);
        Statistic.printParametersOfField();

        LiveCycle cycle = new LiveCycle();
        cycle.repeatCycle(3);
    }
}
