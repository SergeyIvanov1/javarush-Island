package ru.javarush.sergeyivanov.island.ContentOfIsland;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;

public abstract class Nature {
    Location location;
    public static final int ALL_OBJECT = 0;

    protected double weight;
    protected int maxCountIntoCell;
    protected int speedMove;
    protected double amountNeedFood;

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public double getWeight() {
        return weight;
    }

    public double getAmountNeedFood() {
        return amountNeedFood;
    }
}
