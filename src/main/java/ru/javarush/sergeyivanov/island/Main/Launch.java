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

        LiveCycle cycle = new LiveCycle();
        cycle.repeatCycle(3);

        Buffalo buffalo = new Buffalo();
        Caterpillar caterpillar = new Caterpillar();
        Deer deer = new Deer();
        Duck duck = new Duck();
        Goat goat = new Goat();
        Horse horse = new Horse();
        Mouse mouse = new Mouse();
        Rabbit rabbit = new Rabbit();
        Sheep sheep = new Sheep();
        WildBoar wildBoar = new WildBoar();
        Bear bear = new Bear();
        Boa boa = new Boa();
        Eagle eagle = new Eagle();
        Fox fox = new Fox();
        Wolf wolf = new Wolf();
        extracted(buffalo);
        extracted(caterpillar);
        extracted(deer);
        extracted(duck);
        extracted(goat);
        extracted(horse);
        extracted(mouse);
        extracted(rabbit);
        extracted(sheep);
        extracted(wildBoar);
        extracted(bear);
        extracted(boa);
        extracted(eagle);
        extracted(fox);
        extracted(wolf);
    }

    private static void extracted(Animal animal) {
        System.out.println(animal.getClass().getSimpleName() + ":");
        for (Map.Entry<Class<? extends Nature>, Integer> ration: animal.getRation().entrySet()) {
            Class<? extends Nature> key = ration.getKey();
            Integer value = ration.getValue();
            System.out.println(key.getSimpleName() + " = " + value);
        }
        System.out.println();
    }
}
