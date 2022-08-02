package ru.javarush.sergeyivanov.island.ContentOfIsland.Field;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;

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

    public <T> BlockingQueue<T> getTargetQueue(Class<T> t) {
        if (Predator.class.isAssignableFrom(t)) {
            return (BlockingQueue<T>) getPredators();
        } else if (Herbivore.class.isAssignableFrom(t)) {
            return (BlockingQueue<T>) getHerbivores();
        } else if (Plant.class.isAssignableFrom(t)) {
            return (BlockingQueue<T>) getPlants();
        }
        return new LinkedBlockingQueue<>();
    }

    public void feedAnimals(){
        for (Animal predator: predators) {
            for (Animal herbivore: herbivores) {
//                herbivores.contains()
            }
        }

    }
}
