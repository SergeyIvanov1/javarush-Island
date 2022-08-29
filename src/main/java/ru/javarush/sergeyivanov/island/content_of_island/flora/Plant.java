package ru.javarush.sergeyivanov.island.content_of_island.flora;

import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.content_of_island.field.Location;
import ru.javarush.sergeyivanov.island.inicialization.Parameters;

import java.util.Map;

public abstract class Plant extends Nature {
    protected String namePlant = this.getClass().getSimpleName();


    public Plant(Parameters parameters) {
        this.parameters = parameters;
//        super(parameters);
        initFieldsClass();
    }

    private void initFieldsClass() {
        Map<String, Number> cacheSettings = parameters.getCacheSettings().get(namePlant);
        weight = (double) cacheSettings.get("weight");
        maxObjInCell = (int) cacheSettings.get("maxObjInCell");
    }


}
