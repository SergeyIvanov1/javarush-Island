package ru.javarush.sergeyivanov.island.ContentOfIsland.Field;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Location implements Runnable {

//    private final BlockingQueue<Predator> predators = new LinkedBlockingQueue<>();
//    private final BlockingQueue<Herbivore> herbivores = new LinkedBlockingQueue<>();
//    private final BlockingQueue<Plant> plants = new LinkedBlockingQueue<>();

    Map<Class<? extends Nature>, BlockingQueue<? extends Nature>> mapQueuesNatureObj = new ConcurrentHashMap<>();

    {
        for (Map.Entry<Class<? extends Nature>, Integer> entry : InitParameters.cacheNatureObj.entrySet()) {
            mapQueuesNatureObj.put(entry.getKey(), new LinkedBlockingQueue<>());
        }

    }

    public Map<Class<? extends Nature>, BlockingQueue<? extends Nature>> getMapQueuesNatureObj() {
        return mapQueuesNatureObj;
    }

//    public BlockingQueue<Predator> getPredators() {
//        return predators;
//    }
//
//    public BlockingQueue<Herbivore> getHerbivores() {
//        return herbivores;
//    }

//    public BlockingQueue<Plant> getPlants() {
//        return plants;
//    }

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

//    public BlockingQueue<? extends Nature> getStorageNatureObjs(Class<? extends Nature> aClass) {
//        if (Predator.class.isAssignableFrom(aClass)) {
//            return getPredators();
//        } else if (Herbivore.class.isAssignableFrom(aClass)) {
//            return getHerbivores();
//        } else if (Plant.class.isAssignableFrom(aClass)) {
//            return getPlants();
//        }
//        return new LinkedBlockingQueue<>();
//    }

    @Override
    public void run() {
        Grass grass = new Grass();
        int maxObjInCell = grass.maxObjInCell;
        if (mapQueuesNatureObj.get(Grass.class).size() < maxObjInCell){

//        if (plants.size() < maxObjInCell){
//            restorePlants(maxObjInCell);
            restoreGrass(maxObjInCell);
        }
    }

//    private void restorePlants(int maxObjInCell) {
//        int difference = maxObjInCell - plants.size();
//        for (int i = 0; i < difference; i++) {
//            try {
//                plants.put(new Grass());
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
}
