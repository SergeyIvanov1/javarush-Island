package ru.javarush.sergeyivanov.island.content_of_island.field;

import ru.javarush.sergeyivanov.island.content_of_island.flora.Grass;
import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.inicialization.Parameters;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

public class Location implements Runnable {

    private final Map<Class<? extends Nature>, Queue<? extends Nature>> mapQueuesNatureObj = new ConcurrentHashMap<>();

    {
        for (Map.Entry<Class<? extends Nature>, Integer> entry : Parameters.cacheNatureObj.entrySet()) {
            mapQueuesNatureObj.put(entry.getKey(), new LinkedBlockingQueue<>());
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
