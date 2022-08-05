package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public abstract class Animal extends Nature {

    protected Map<Class<Nature>, Integer> ration;
    protected double satiety;
    protected boolean isMale;

    public Animal(double weight, int maxCountIntoCell, int speedMove, double amountNeedFood) {
        this.weight = weight;
        this.maxCountIntoCell = maxCountIntoCell;
        this.speedMove = speedMove;
        this.amountNeedFood = amountNeedFood;

        Random random = new Random();
        satiety = random.nextDouble(amountNeedFood/2, amountNeedFood);
        isMale = random.nextBoolean();
    }

    public abstract void eat();
    public abstract void multiply();
    public abstract void move();
    public abstract void die();
    public abstract Optional<Double> findFood(BlockingQueue<? extends Animal> animals);


}
