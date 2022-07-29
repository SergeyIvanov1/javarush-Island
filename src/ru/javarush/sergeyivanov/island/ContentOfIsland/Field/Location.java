package ru.javarush.sergeyivanov.island.ContentOfIsland.Field;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Buffalo;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Fox;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Location {

    private BlockingQueue<Predator> predators = new LinkedBlockingQueue<>();
    private BlockingQueue<Herbivore> herbivores = new LinkedBlockingQueue<>();
    private BlockingQueue<Plant> plants = new LinkedBlockingQueue<>();

//    {
//        predators.add(new Fox());
//        herbivores.add(new Buffalo());
//        plants.add(new Grass());
//    }

    public BlockingQueue<Predator> getPredators() {
        return predators;
    }

    public BlockingQueue<Herbivore> getHerbivores() {
        return herbivores;
    }

    public BlockingQueue<Plant> getPlants() {
        return plants;
    }

    public <T> BlockingQueue<T> getTargetQueue(Class<T> t) {
        if (Predator.class == t) {
            return (BlockingQueue<T>) getPredators();
        } else if (Herbivore.class == t) {
            return (BlockingQueue<T>) getHerbivores();
        } else if (Plant.class == t) {
            return (BlockingQueue<T>) getPlants();
        }
        return new LinkedBlockingQueue<>();
    }

}
