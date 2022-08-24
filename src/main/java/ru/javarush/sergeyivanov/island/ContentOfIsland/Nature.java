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
    public int maxObjInCell;
    protected int rangeMove;
    protected double amountNeedFood;
    protected int amountChildren;
    protected int amountCyclesLife;

    {
        nameObj = this.getClass().getSimpleName();
        initFieldsClass();
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

    private void initFieldsClass() {
        Map<String, Number> settingsFromDataBase = InitParameters.cacheSettings.get(nameObj);
        weight = (double)settingsFromDataBase.get("weight");
        maxObjInCell = (int)settingsFromDataBase.get("maxObjInCell");
        rangeMove = (int)settingsFromDataBase.get("rangeMove");
        amountNeedFood = (double)settingsFromDataBase.get("amountNeedFood");
        if (Animal.class.isAssignableFrom(this.getClass())) {
            amountChildren = (int) settingsFromDataBase.get("amountChildren");
            amountCyclesLife = (int) settingsFromDataBase.get("amountCycleLive");
        }
    }
}
