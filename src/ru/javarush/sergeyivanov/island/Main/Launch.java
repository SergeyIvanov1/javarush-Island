package ru.javarush.sergeyivanov.island.Main;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Wolf;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Launch {
    public static void main(String[] args) throws NoSuchFieldException {
        Scanner scanner = new Scanner(System.in);

//        System.out.println("Does to run the program manually?");
//        boolean answer = scanner.nextBoolean();
        new InitParameters(false);
        Statistic.printParametersOfField();
//        System.out.println(Arrays.deepToString(Island.getInstance().getField()));

//        Location location = new Location();
//        System.out.println(location.getTargetQueue(Predator.class));
//        System.out.println(location.getTargetQueue(Herbivore.class));
//        System.out.println(location.getTargetQueue(Plant.class));
    }
}
