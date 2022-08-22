package ru.javarush.sergeyivanov.island.ContentOfIsland.Field;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Location {

    private final BlockingQueue<Predator> predators = new LinkedBlockingQueue<>();
    private final BlockingQueue<Herbivore> herbivores = new LinkedBlockingQueue<>();
    private final BlockingQueue<Plant> plants = new LinkedBlockingQueue<>();

    public BlockingQueue<Predator> getPredators() {
        return predators;
    }

    public BlockingQueue<Herbivore> getHerbivores() {
        return herbivores;
    }

    public BlockingQueue<Plant> getPlants() {
        return plants;
    }

    public BlockingQueue<? extends Nature> getStorageNatureObjs(Class<? extends Nature> aClass) {
        if (Predator.class.isAssignableFrom(aClass)) {
            return getPredators();
        } else if (Herbivore.class.isAssignableFrom(aClass)) {
            return getHerbivores();
        } else if (Plant.class.isAssignableFrom(aClass)) {
            return getPlants();
        }
        return new LinkedBlockingQueue<>();
    }

//    public void getCountObjects(BlockingQueue<? extends Nature> queue){
//        Map<String, >
//
//    }
}
