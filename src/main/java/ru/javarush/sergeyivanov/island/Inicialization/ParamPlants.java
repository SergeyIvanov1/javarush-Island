package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Shrub;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.util.HashMap;
import java.util.Map;

public class ParamPlants {
    private static final Map<Class<? extends Nature>, Integer> map = new HashMap<>();
    static  {
        map.put(Grass.class, 150_000);
        map.put(Shrub.class, 9_000);

    }

    public static Map<Class<? extends Nature>, Integer> getMap() {
        return map;
    }
}
