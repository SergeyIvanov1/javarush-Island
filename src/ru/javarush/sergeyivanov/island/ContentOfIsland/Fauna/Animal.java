package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

public abstract class Animal extends Nature {
    private double weight;
    private int maxCountIntoCell;
    private int speedMove;
    private double amountNeedFood;

    public Animal(double weight, int maxCountIntoCell, int speedMove, double amountNeedFood) {
        this.weight = weight;
        this.maxCountIntoCell = maxCountIntoCell;
        this.speedMove = speedMove;
        this.amountNeedFood = amountNeedFood;
    }

    public abstract void eat();
    public abstract void multiply();
    public abstract void move();
    public abstract void die();
}
