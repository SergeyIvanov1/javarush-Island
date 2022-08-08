package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.*;

public class Bear extends Predator {

    {
        ration.put(Boa.class, 80);
        ration.put(Horse.class, 40);
        ration.put(Deer.class, 80);
        ration.put(Rabbit.class, 80);
        ration.put(Mouse.class, 90);
        ration.put(Goat.class, 70);
        ration.put(Sheep.class, 70);
        ration.put(WildBoar.class, 50);
        ration.put(Buffalo.class, 20);
        ration.put(Duck.class, 10);
    }

    public Bear() {
        super(500, 5, 2, 80);
    }

    @Override
    public void move() {

    }

    @Override
    public void die() {

    }
}
