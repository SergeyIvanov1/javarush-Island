package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Shrub;

public class Duck extends Herbivore{

    {
        ration.put(Grass.class, 100);
        ration.put(Shrub.class, 100);
        ration.put(Caterpillar.class, 90);
    }

    public Duck() {
        super(1, 200, 4, 0.15);
    }
}
