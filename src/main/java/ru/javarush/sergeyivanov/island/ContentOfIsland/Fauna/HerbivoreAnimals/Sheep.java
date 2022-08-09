package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Shrub;

public class Sheep extends Herbivore{

    {
        ration.put(Grass.class, 100);
        ration.put(Shrub.class, 75);
    }

    public Sheep() {
        super(70, 140, 3, 15);
    }

    @Override
    public void die() {

    }
}
