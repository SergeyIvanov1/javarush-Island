package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Shrub;

public class Buffalo extends Herbivore{

    {
        ration.put(Grass.class, 100);
        ration.put(Shrub.class, 100);
    }

    public Buffalo() {
        super(700, 10, 3, 100);
    }

}
