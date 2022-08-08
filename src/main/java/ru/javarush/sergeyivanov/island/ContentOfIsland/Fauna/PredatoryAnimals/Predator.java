package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public abstract class Predator extends Animal {
    public Predator(int weight, int maxCountIntoCell, int speedMove, int amountNeedFood) {
        super(weight, maxCountIntoCell, speedMove, amountNeedFood);
    }
}
