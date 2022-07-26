package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;

public abstract class Herbivore extends Animal {

    public Herbivore(double weight, int maxCountIntoCell, int speedMove, double amountNeedFood) {
        super(weight, maxCountIntoCell, speedMove, amountNeedFood);
    }
}
