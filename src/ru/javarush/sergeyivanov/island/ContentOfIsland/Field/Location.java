package ru.javarush.sergeyivanov.island.ContentOfIsland.Field;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Location {

    private BlockingQueue<Predator> predators = new LinkedBlockingQueue<>();
    private BlockingQueue<Herbivore> herbivores = new LinkedBlockingQueue<>();
    private BlockingQueue<Plant> plants = new LinkedBlockingQueue<>();

}
