package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;

public abstract class Predator extends Animal {
    public Predator(int weight, int maxCountIntoCell, int speedMove, int amountNeedFood) {
        super(weight, maxCountIntoCell, speedMove, amountNeedFood);
    }
}
