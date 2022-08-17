package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.*;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.util.HashMap;
import java.util.Map;

public class ParamPredators {
    private static final Map<Class<? extends Nature>, Integer> map = new HashMap<>();
    static  {
        map.put(Bear.class, 45); //150
        map.put(Boa.class, 45); //900
        map.put(Eagle.class, 45); //600
        map.put(Fox.class, 45); //900
        map.put(Wolf.class, 45);//900
    }

    public static Map<Class<? extends Nature>, Integer> getMapOfAnimals() {
        return map;
    }
}
