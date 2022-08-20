package ru.javarush.sergeyivanov.island.ContentOfIsland;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.Main.UtilProcessor;

import java.util.HashMap;
import java.util.Map;

public abstract class Nature {
    Location location;

    protected int indexLineField;
    protected int IndexColumnField;

//    String nameObj;
//    protected Map<Class<? extends Animal>, Integer> ration = new HashMap<>();
//    public Map<String, Map<Class<? extends Animal>, Integer>> cacheRation = new HashMap<>();
    protected double weight;
    protected int maxObjInCell;
    protected int rangeMove;
    protected double amountNeedFood;
    protected int amountChildren;
    protected int amountCycleLive;

    {
//        nameObj = this.getClass().getSimpleName();
//
//        if (Animal.class.isAssignableFrom(this.getClass())) {
//            if (cacheRation.containsKey(nameObj)) {
//                ration = cacheRation.get(nameObj);
//            } else {
//                UtilProcessor.fillRationFromDataBase(nameObj, ration);
//                cacheRation.put(nameObj, ration);
//            }
//        }
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
