package ru.javarush.sergeyivanov.island.ContentOfIsland.Field;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Location {

    private BlockingQueue<Predator> predators = new LinkedBlockingQueue<>();
    private BlockingQueue<Herbivore> herbivores = new LinkedBlockingQueue<>();
    private BlockingQueue<Plant> plants = new LinkedBlockingQueue<>();

    public BlockingQueue<Predator> getPredators() {
        return predators;
    }

    public BlockingQueue<Herbivore> getHerbivores() {
        return herbivores;
    }

    public BlockingQueue<Plant> getPlants() {
        return plants;
    }

    //метод не проверен
    public <T> BlockingQueue<? super T> getQueueForAllocate(Class<T> t){
        if (Predator.class == t) {
            return (BlockingQueue<? super T>) getPredators();
        } else if (Herbivore.class == t) {
            return (BlockingQueue<? super T>) getHerbivores();
        } else if (Plant.class == t){
            return (BlockingQueue<? super T>) getPlants();
        }
        return new LinkedBlockingQueue<>();
    }

}
