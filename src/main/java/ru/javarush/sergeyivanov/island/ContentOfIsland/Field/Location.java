package ru.javarush.sergeyivanov.island.ContentOfIsland.Field;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

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

    public BlockingQueue<? extends Nature> getTargetQueue(Class<? extends Nature> t) {
        if (Predator.class.isAssignableFrom(t)) {
            return predators;
        } else if (Herbivore.class.isAssignableFrom(t)) {
            return herbivores;
        } else if (Plant.class.isAssignableFrom(t)) {
            return plants;
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
