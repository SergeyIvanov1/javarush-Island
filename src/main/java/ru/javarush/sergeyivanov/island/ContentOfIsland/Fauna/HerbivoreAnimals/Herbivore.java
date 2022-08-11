package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;

public abstract class Herbivore extends Animal {

    public Herbivore(double weight, int maxCountIntoCell, int rangeMove, double amountNeedFood) {
        super(weight, maxCountIntoCell, rangeMove, amountNeedFood);
    }

    @Override
    public void run() {

    }
}
