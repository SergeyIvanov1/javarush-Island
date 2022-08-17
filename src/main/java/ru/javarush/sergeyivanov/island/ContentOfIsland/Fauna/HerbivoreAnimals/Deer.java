package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Shrub;

public class Deer extends Herbivore{

    {
        ration.put(Grass.class, 100);
        ration.put(Shrub.class, 100);
    }

    public Deer() {
        super(300, 20, 4, 50);
    }
}
