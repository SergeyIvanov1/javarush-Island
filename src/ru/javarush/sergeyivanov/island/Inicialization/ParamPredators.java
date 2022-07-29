package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.*;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ParamPredators {
    Map<Class, Integer> map = new HashMap<>();
    {
        map.put(Bear.class, 150);
        map.put(Boa.class, 900);
        map.put(Eagle.class, 600);
        map.put(Fox.class, 900);
        map.put(Wolf.class, 900);
    }

}
