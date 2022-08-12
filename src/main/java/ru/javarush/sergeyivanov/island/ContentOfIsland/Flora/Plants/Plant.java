package ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

public abstract class Plant extends Nature {
    public Plant(double weight, int maxCountIntoCell, int rangeMove, double amountNeedFood) {
        this.weight = weight;
        this.maxCountInsideCell = maxCountIntoCell;
        this.rangeMove = rangeMove;
        this.amountNeedFood = amountNeedFood;
    }
}
