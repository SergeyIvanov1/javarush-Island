package ru.javarush.sergeyivanov.island.ContentOfIsland.Field;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class Location implements Runnable {

    Map<Class<? extends Nature>, Queue<? extends Nature>> mapQueuesNatureObj = new ConcurrentHashMap<>();

    {
        for (Map.Entry<Class<? extends Nature>, Integer> entry : InitParameters.cacheNatureObj.entrySet()) {
            mapQueuesNatureObj.put(entry.getKey(), new LinkedList<>());
        }

    }

    public Map<Class<? extends Nature>, Queue<? extends Nature>> getMapQueuesNatureObj() {
        return mapQueuesNatureObj;
    }

    public Queue<? extends Nature> getQueueOfNatureObjects(Class<? extends Nature> aClass) {
        return mapQueuesNatureObj.get(aClass);
    }

    private void restoreGrass(int maxObjInCell) {
        Queue<Grass> queueGrass = (Queue<Grass>)mapQueuesNatureObj.get(Grass.class);
        int amountGrassInQueue = queueGrass.size();
        int difference = maxObjInCell - amountGrassInQueue;
        for (int i = 0; i < difference; i++) {
            queueGrass.add(new Grass());
        }
    }

    @Override
    public void run() {
        Grass grass = new Grass();
        int maxObjInCell = grass.maxObjInCell;
        if (mapQueuesNatureObj.get(Grass.class).size() < maxObjInCell){
            restoreGrass(maxObjInCell);
        }
    }
}
