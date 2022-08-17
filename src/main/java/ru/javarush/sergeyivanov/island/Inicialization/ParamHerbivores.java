package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.*;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.util.HashMap;
import java.util.Map;

public class ParamHerbivores {

    private static final Map<Class<? extends Nature>, Integer> map = new HashMap<>();
    static {
        map.put(Buffalo.class, 3); //300
        map.put(Caterpillar.class, 300);//30_000
        map.put(Deer.class, 6); //600
        map.put(Duck.class, 60); //6_000
        map.put(Goat.class, 42); //4200
        map.put(Horse.class, 6); //600
        map.put(Mouse.class, 150); //15_000
        map.put(Rabbit.class, 45); // 4500
        map.put(Sheep.class, 42); //4200
        map.put(WildBoar.class, 15); // 1500
    }

    public static Map<Class<? extends Nature>, Integer> getMapOfAnimals() {
        return map;
    }
}
