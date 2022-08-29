package ru.javarush.sergeyivanov.island.content_of_island;

import ru.javarush.sergeyivanov.island.content_of_island.field.Location;
import ru.javarush.sergeyivanov.island.inicialization.Parameters;

public abstract class Nature {
    protected int indexLineField;
    protected int IndexColumnField;

    protected double weight;
    protected int maxObjInCell;
    protected Parameters parameters;
    Location location;

    public int getMaxObjInCell() {
        return maxObjInCell;
    }

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
