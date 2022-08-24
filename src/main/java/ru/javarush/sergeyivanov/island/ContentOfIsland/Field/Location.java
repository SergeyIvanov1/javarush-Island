package ru.javarush.sergeyivanov.island.ContentOfIsland.Field;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Location implements Runnable {

    Map<Class<? extends Nature>, BlockingQueue<? extends Nature>> mapQueuesNatureObj = new ConcurrentHashMap<>();

    {
        for (Map.Entry<Class<? extends Nature>, Integer> entry : InitParameters.cacheNatureObj.entrySet()) {
            mapQueuesNatureObj.put(entry.getKey(), new LinkedBlockingQueue<>());
        }

    }

    public Map<Class<? extends Nature>, BlockingQueue<? extends Nature>> getMapQueuesNatureObj() {
        return mapQueuesNatureObj;
    }

    public BlockingQueue<? extends Nature> getQueueOfNatureObjects(Class<? extends Nature> aClass) {
        return mapQueuesNatureObj.get(aClass);
    }

    private void restoreGrass(int maxObjInCell) {
        BlockingQueue<Grass> queueGrass = (BlockingQueue<Grass>)mapQueuesNatureObj.get(Grass.class);
        int amountGrassInQueue = queueGrass.size();
        int difference = maxObjInCell - amountGrassInQueue;
        for (int i = 0; i < difference; i++) {
            try {
                queueGrass.put(new Grass());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
