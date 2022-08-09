package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.*;

public class Eagle extends Predator {

    {
        ration.put(Fox.class, 10);
        ration.put(Rabbit.class, 90);
        ration.put(Mouse.class, 90);
        ration.put(Duck.class, 80);
    }

    public Eagle() {
        super(6, 20, 3, 1);
    }

    @Override
    public void die() {

    }
}
