package ru.javarush.sergeyivanov.island.ContentOfIsland;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;

public abstract class Nature {
    Location location;

    protected int indexLineField;
    protected int IndexColumnField;

    protected double weight;
    protected int maxCountInsideCell;
    protected int rangeMove;
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

    public int getIndexLineField() {
        return indexLineField;
    }

    public void setIndexLineField(int indexLineField) {
        this.indexLineField = indexLineField;
    }

    public int getIndexColumnField() {
        return IndexColumnField;
    }

    public void setIndexColumnField(int indexColumnField) {
        this.IndexColumnField = indexColumnField;
    }

}
