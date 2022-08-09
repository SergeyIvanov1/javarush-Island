package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.*;

public class Wolf extends Predator {

    {
        ration.put(Horse.class, 10);
        ration.put(Deer.class, 15);
        ration.put(Rabbit.class, 60);
        ration.put(Mouse.class, 80);
        ration.put(Goat.class, 60);
        ration.put(Sheep.class, 70);
        ration.put(WildBoar.class, 15);
        ration.put(Buffalo.class, 10);
        ration.put(Duck.class, 40);
    }

    public Wolf() {
        super(50, 30, 3, 8);
    }

    @Override
    public void die() {

    }
}
