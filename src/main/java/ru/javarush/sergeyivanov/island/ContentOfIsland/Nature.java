package ru.javarush.sergeyivanov.island.ContentOfIsland;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;
import java.util.Map;

public abstract class Nature {
    Location location;

    protected int indexLineField;
    protected int IndexColumnField;

    String nameObj;

    protected double weight;
    protected int maxObjInCell;
    protected int rangeMove;
    protected double amountNeedFood;
    protected int amountChildren;
    protected int amountCyclesLife;

    {
        nameObj = this.getClass().getSimpleName();
        initFields();
    }

    private void initFields() {
        Map<String, Number> settings = InitParameters.cacheSettings.get(nameObj);
        weight = (double)settings.get("weight");
        maxObjInCell = (int)settings.get("maxObjInCell");
        rangeMove = (int)settings.get("rangeMove");
        amountNeedFood = (double)settings.get("amountNeedFood");
        if (Animal.class.isAssignableFrom(this.getClass())) {
            amountChildren = (int) settings.get("amountChildren");
            amountCyclesLife = (int) settings.get("amountCycleLive");
        }
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
