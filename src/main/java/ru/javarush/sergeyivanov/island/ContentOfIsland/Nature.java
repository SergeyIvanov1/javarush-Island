package ru.javarush.sergeyivanov.island.ContentOfIsland;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;

public abstract class Nature {
    Location location;

    int IndexLineLocation;
    int IndexColumnLocation;

    public static final int ALL_OBJECT = 0;

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

    public int getIndexLineLocation() {
        return IndexLineLocation;
    }

    public void setIndexLineLocation(int indexLineLocation) {
        this.IndexLineLocation = indexLineLocation;
    }

    public int getIndexColumnLocation() {
        return IndexColumnLocation;
    }

    public void setIndexColumnLocation(int indexColumnLocation) {
        this.IndexColumnLocation = indexColumnLocation;
    }

}
