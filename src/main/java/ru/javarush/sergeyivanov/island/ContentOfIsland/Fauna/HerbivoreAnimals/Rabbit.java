package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Shrub;

public class Rabbit extends Herbivore{

    {
        ration.put(Grass.class, 100);
        ration.put(Shrub.class, 75);
    }

    public Rabbit() {
        super(2, 150, 2, 0.45);
    }

    @Override
    public void move() {

    }

    @Override
    public void die() {

    }
}
