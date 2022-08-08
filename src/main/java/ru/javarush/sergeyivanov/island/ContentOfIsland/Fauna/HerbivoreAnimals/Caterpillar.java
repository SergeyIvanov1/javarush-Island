package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Shrub;

public class Caterpillar extends Herbivore{

    {
        ration.put(Grass.class, 100);
        ration.put(Shrub.class, 75);
    }

        public Caterpillar() {
            super(0.01, 1000, 0, 0);
        }

     @Override
    public void move() {

    }

    @Override
    public void die() {

    }
}
