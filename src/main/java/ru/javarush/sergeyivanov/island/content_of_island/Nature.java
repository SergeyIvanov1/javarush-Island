package ru.javarush.sergeyivanov.island.content_of_island;

import ru.javarush.sergeyivanov.island.content_of_island.fauna.Animal;
import ru.javarush.sergeyivanov.island.content_of_island.field.Location;
import ru.javarush.sergeyivanov.island.inicialization.Parameters;
import java.util.Map;

public abstract class Nature {
    protected String nameAnimal = this.getClass().getSimpleName();
    protected int indexLineField;
    protected int IndexColumnField;
    protected double weight;
    protected int maxObjInCell;
    protected int rangeMove;
    protected double amountNeedFood;
    protected int amountChildren;
    protected int amountCyclesLife;
    protected Parameters parameters;
    Location location;


    public Nature(Parameters parameters) {
        this.parameters = parameters;
        initFieldsClass();
    }

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

    private void initFieldsClass() {
        Map<String, Number> settingsFromDataBase = parameters.getCacheSettings().get(nameAnimal);
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
