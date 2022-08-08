package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Shrub;

public class Mouse extends Herbivore {

    {
        ration.put(Grass.class, 100);
        ration.put(Shrub.class, 75);
        ration.put(Caterpillar.class, 75);
    }

    public Mouse() {
        super(0.05, 500, 1, 0.01);
    }

    @Override
    public void move() {

    }

    @Override
    public void die() {

    }
}
