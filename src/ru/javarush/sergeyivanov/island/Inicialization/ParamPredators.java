package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.*;

import java.util.HashMap;
import java.util.Map;

public class ParamPredators {
    private static final Map<Class, Integer> map = new HashMap<>();
    static  {
        map.put(Bear.class, 150);
        map.put(Boa.class, 900);
        map.put(Eagle.class, 600);
        map.put(Fox.class, 900);
        map.put(Wolf.class, 900);
    }

    public static Map<Class, Integer> getMap() {
        return map;
    }
}
