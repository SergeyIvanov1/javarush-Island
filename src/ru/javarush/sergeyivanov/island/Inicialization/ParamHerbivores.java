package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ParamHerbivores {

    Map<Class, Integer> map = new HashMap<>();
    {
        map.put(Buffalo.class, 300);
        map.put(Caterpillar.class, 30_000);
        map.put(Deer.class, 600);
        map.put(Duck.class, 6_000);
        map.put(Goat.class, 4200);
        map.put(Horse.class, 600);
        map.put(Mouse.class, 15_000);
        map.put(Rabbit.class, 4500);
        map.put(Sheep.class, 4200);
        map.put(WildBoar.class, 1500);
    }
}
