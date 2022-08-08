package ru.javarush.sergeyivanov.island.Main;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Buffalo;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Wolf;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
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
//        Scanner scanner = new Scanner(System.in);

//        System.out.println("Does to run the program manually?");
//        boolean answer = scanner.nextBoolean();
        new InitParameters(false);
        Statistic.printParametersOfField();

//        BlockingQueue<Predator> predators = Island.getInstance().getField()[2][3].getPredators();
//        for (Predator predator: predators) {
//            System.out.println(predator.getLocation());
//        }

//        System.out.println(Arrays.deepToString(Island.getInstance().getField()));

//        Location location = new Location();

//        Wolf wolf = new Wolf();
//        wolf.eat();
//        Buffalo buffalo = new Buffalo();
//        buffalo.eat();
    }
}
